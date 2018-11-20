package com.angellift.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.angellift.utils.Const.PICK_IMAGE_REQUEST;


public class ConstMethod {


    private Context context;

    public ConstMethod(Context context) {
        this.context = context;
        ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading......");

    }

    // isValidEmailAddress: Check the email address is OK
    public static boolean isValidEmailAddress(String emailAddress) {

        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {

            return false;
        }
        Log.e("return ", "====>" + matcher);
        return true;
    }

    // check the password mst be more then 6 latter
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        if (password.length() > 5) {
            return true;
        }
        return false;
        //  final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        // pattern = Pattern.compile(PASSWORD_PATTERN);
        //  matcher = pattern.matcher(password);

        //return matcher.matches();
    }

    // empty field validation
    public static boolean validate(EditText[] fields, String[] msg) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().trim().length() <= 0) {
                currentField.requestFocus();
                currentField.setError(msg[i]);
                return false;

            }
        }
        return true;
    }

    // check the internet is on or not
    public static boolean isInternetOn(Activity activityName) {
        try {
            NetworkInfo activeNetwork = ((ConnectivityManager) activityName.getSystemService(Context.CONNECTIVITY_SERVICE))
                    .getActiveNetworkInfo();
            if (activeNetwork == null || !activeNetwork.isConnected()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Alert DialogBox
    public static void NetworkAlert(Activity context) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Turn On you Mobile Data");
        alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // select iamge from gallary
    public static void SelectFile(Activity activity) {
//        Intent intent = new Intent();
//        intent.setType("*/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        String[] mimeTypes = {"image/*", "application/pdf"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";

            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }

            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        activity.startActivityForResult(Intent.createChooser(intent, "Select"), PICK_IMAGE_REQUEST);


    }

    // get real path of file path from uri
    public static String getPath(Uri uri, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = activity.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        Log.e("PATH", "******>" + path);
        return path;
    }

    /*//Requesting permission
    public static boolean requestStoragePermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return true;

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
      //  ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        return false;
    }*/
    public static String DateFunctionForSearch(Context context, final TextView date) {

        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int monthSel = month + 1;
                String formattedMonth = "" + month;
                String formattedDayOfMonth = "" + dayOfMonth;

                if (monthSel < 10) {

                    formattedMonth = "0" + monthSel;
                }
                if (dayOfMonth < 10) {

                    formattedDayOfMonth = "0" + dayOfMonth;
                }
                date.setText(year + "/" + formattedMonth + "/" + formattedDayOfMonth);
                // date.setText("" + dayOfMonth + "-" + month + "-" + year);
            }
        }, mYear, mMonth, mDay);
        dialog.setTitle("Select Date");
        dialog.show();
        return date.getText().toString();
    }

    //DatePicker Function
    public static void DateFunction(Context context, final TextView date) {
        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                //String date = new SimpleDateFormat("MM/dd/yyyy").format(mcurrentDate.getTime());
                //att_date.setText(date);
                month = month + 1;
                date.setText("" + year + "-" + month + "-" + dayOfMonth);
            }
        }, mYear, mMonth, mDay);
        dialog.setTitle("Select Date");
        dialog.show();
    }

    public static void DobDateFunction(Context context, final TextView date) {

        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int monthSel = month + 1;
                String formattedMonth = "" + month;
                String formattedDayOfMonth = "" + dayOfMonth;

                if (monthSel < 10) {

                    formattedMonth = "0" + monthSel;
                }
                if (dayOfMonth < 10) {

                    formattedDayOfMonth = "0" + dayOfMonth;
                }
                date.setText(formattedDayOfMonth + "-" + formattedMonth + "-" + year);

                // date.setText("" + dayOfMonth + "-" + month + "-" + year);
            }
        }, mYear, mMonth, mDay);
        dialog.setTitle("Select Date");
        dialog.show();
    }

    public static void dateForamtYYYMMDD(Context context, final TextView date) {

        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int monthSel = month + 1;
                String formattedMonth = "" + month;
                String formattedDayOfMonth = "" + dayOfMonth;

                if (monthSel < 10) {

                    formattedMonth = "0" + monthSel;
                }
                if (dayOfMonth < 10) {

                    formattedDayOfMonth = "0" + dayOfMonth;
                }
                date.setText(year + "/" + formattedMonth + "/" + formattedDayOfMonth);

                // date.setText("" + dayOfMonth + "-" + month + "-" + year);
            }
        }, mYear, mMonth, mDay);
        dialog.setTitle("Select Date");
        dialog.show();
    }

    public static void dateForamtYYYMMDDWithDesh(Context context, final TextView date) {

        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int monthSel = month + 1;
                String formattedMonth = "" + month;
                String formattedDayOfMonth = "" + dayOfMonth;

                if (monthSel < 10) {

                    formattedMonth = "0" + monthSel;
                }
                if (dayOfMonth < 10) {

                    formattedDayOfMonth = "0" + dayOfMonth;
                }
                date.setText(year + "-" + formattedMonth + "-" + formattedDayOfMonth);

                // date.setText("" + dayOfMonth + "-" + month + "-" + year);
            }
        }, mYear, mMonth, mDay);
        mcurrentDate.add(Calendar.YEAR, -10);
        dialog.getDatePicker().setMaxDate(mcurrentDate.getTimeInMillis());
        dialog.setTitle("Select Date");
        dialog.show();
    }

    public static void DateAndTimeFunction(final Context context, final TextView date) {

        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // String datestr = new SimpleDateFormat("yyyy-MM-dd").format(mcurrentDate.getTime());
                int monthSel = month + 1;
                String formattedMonth = "" + month;
                String formattedDayOfMonth = "" + dayOfMonth;

                if (monthSel < 10) {

                    formattedMonth = "0" + monthSel;
                }
                if (dayOfMonth < 10) {

                    formattedDayOfMonth = "0" + dayOfMonth;
                }
                TimePiCkerFunction(context, date, +year + "-" + formattedMonth + "-" + formattedDayOfMonth);
            }
        }, mYear, mMonth, mDay);

        dialog.setTitle("Select Date");
        dialog.show();
    }

    private static void TimePiCkerFunction(Context context, final TextView date, final String datestr) {
        Calendar mcurrentTime = Calendar.getInstance();
        final int mhour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);


        TimePickerDialog dialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String formattedHour = "" + selectedHour;
                String formattedDayOfMinute = "" + selectedMinute;

                if (selectedHour < 10) {

                    formattedHour = "0" + selectedHour;
                }
                if (selectedMinute < 10) {

                    formattedDayOfMinute = "0" + selectedMinute;
                }

                date.setText(datestr + " " + formattedHour + ":" + formattedDayOfMinute);
            }
        }, mhour, minute, true);
        dialog.setTitle("Select Date");
        dialog.show();
    }


    public static boolean isValidMail(String email) {
        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(EMAIL_STRING).matcher(email).matches();
    }

    public boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 6 || phone.length() > 13) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

    public static void LodDebug(String title, String message) {
        //            ConstMethod.LodDebug(TAG,"select patient");
        Log.d(title, " message --- > " + message);
    }

    public static void showToast(Context context, String message) {
        //            ConstMethod.LodDebug(TAG,"select patient");
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static ProgressDialog showProgressDialog(Context context, String message) {

        //         final ProgressDialog myDialog = ConstMethod.showProgressDialog(this, getResources().getString(R.string.please_wait));
        //                  myDialog.dismiss();

        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage(message);
        //   m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //   m_Dialog.setCancelable(false);
        m_Dialog.show();
        return m_Dialog;
    }
    public static String random(int size) {
        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            // Generate 20 integers 0..20
            for (int i = 0; i < size; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedToken.toString();
    }

}

