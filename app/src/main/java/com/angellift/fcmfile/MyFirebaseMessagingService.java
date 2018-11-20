package com.angellift.fcmfile;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;

import com.angellift.MainActivity;
import com.angellift.R;
import com.angellift.utils.ConstMethod;

import com.angellift.utils.PreferenceHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;




public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Bitmap bitmap;
    static int i = 1;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    PreferenceHelper pHelper;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        ConstMethod.LodDebug(TAG, "From: " + remoteMessage.getFrom());
        ConstMethod.LodDebug(TAG, "Message noti : " + remoteMessage.getNotification().getBody());
        ConstMethod.LodDebug(TAG, "Message data  " + remoteMessage.getData().toString());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
          //  Log.d(TAG, "Message data data: " + remoteMessage.getData().get("message"));
            try {
                JSONObject jobPush=new JSONObject(remoteMessage.getData());
                String message = jobPush.getString("message");
                sendNotification(message);

            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            ConstMethod.LodDebug(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }

    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {
        ConstMethod.LodDebug(TAG, "Message data sendNotification msg: " + messageBody);
      //  pHelper=new PreferHelper(this);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);


        //Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.beep_cut);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }



}
