package com.angellift.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.angellift.AsynceTask.ServiceAsync;
import com.angellift.MainActivity;
import com.angellift.R;
import com.angellift.utils.Const;
import com.angellift.utils.ConstMethod;
import com.angellift.utils.PreferenceHelper;
import com.angellift.utils.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity  {

    String TAG="LoginActivity";
    Context context;
    EditText edt_login_mno, edt_login_pass;
    Button btn_login_signin;
    TextView btn_login_signup, btn_login_forget_pass;
    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";
    String deviceId;
    private PreferenceHelper prefHelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        init();

        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if (prefHelp.getLogin()) {
            startActivity(new Intent(context, MainActivity.class));
            finish();

        }
        btn_login_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConstMethod.isInternetOn(LoginActivity.this)) {
                    if (TextUtils.isEmpty(edt_login_mno.getText())) {
                        edt_login_mno.requestFocus();
                        edt_login_mno.setError("Enter Mobile No.");

                    }else if (TextUtils.isEmpty(edt_login_pass.getText())) {
                        edt_login_pass.requestFocus();
                        edt_login_pass.setError("Enter Password");

                    } else if (ConstMethod.isValidPassword(edt_login_pass.getText().toString())) {
             getUserLogin();
                    } else {
                        edt_login_pass.requestFocus();
                        edt_login_pass.setError("Enter Valid Password");
                    }
                } else {
                    ConstMethod.NetworkAlert(LoginActivity.this);

                }
            }
        });
        btn_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SignupActivity.class));
            }
        });
    }

    private void getUserLogin() {

        final ProgressDialog myDialog = ConstMethod.showProgressDialog(this, getResources().getString(R.string.please_wait));

        final String id = String.valueOf(edt_login_mno.getText().toString().trim());
        String pass = edt_login_pass.getText().toString().trim();

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter(Const.Params.IDENTITY, id);
        builder.appendQueryParameter(Const.Params.PASSWORD, pass);
        builder.appendQueryParameter(Const.Params.MOBILE_TOKEN, prefHelp.getDeviceToken());
        builder.appendQueryParameter(Const.Params. MOBILE_DEVICE_ID, deviceId);

        Log.e("Login url ", Const.UrlClient.LOGIN_URL);
        Log.e("Login parm", "param  " + builder.toString());

        new ServiceAsync(Const.UrlClient.LOGIN_URL, new ServiceAsync.OnAsyncResult() {
            @Override
            public void onSuccess(String result) {
                Log.e("JSON", "DATA " + result);
                myDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString(Const.Params.STATUS).equals(Const.Params.TRUE))
                    {
                        //String str = jsonObject.getString("status");
                        JSONObject object = jsonObject.getJSONObject(Const.Params.DATA);

                        // get the all data in variable
                        String id1 = object.getString(Const.Params.ID);
                        String user_detail_id = object.getString(Const.Params.C_USERDETAILS_ID);
                        String username = object.getString(Const.Params.USERNAME);
                        String CreatedByUserId = object.getString(Const.Params.C_CREATEBYUSERID );
                        String first_name = object.getString(Const.Params.FIRST_NAME );
                        String email = object.getString(Const.Params.EMAIL);
                        String token = object.getString(Const.Params.TOKEN_ID );
                        String role_id = object.getString(Const.Params.ROLE_ID );


                        prefHelp.putLogin(true);
                        prefHelp.putUserId(id1);
                        prefHelp.putUsername(username);
                        prefHelp.putFIRST_NAME(first_name);
                        prefHelp.putCreatedByUserId(CreatedByUserId);
                        prefHelp.putTOKEN(token);
                        prefHelp.putUserDetailId(user_detail_id);
                        prefHelp.putEmail(email);
                        prefHelp.putROLE_ID(role_id);

                        startActivity(new Intent(context, MainActivity.class));
                    }else {
                        Toast.makeText(context, "Login fail, please try again", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Incorrect Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String result) {
                myDialog.dismiss();
                Toast.makeText(context, "failed" + result, Toast.LENGTH_SHORT).show();
            }

        }, builder).execute();

       // startActivity(new Intent(context, MainActivity.class));
    }

    private void init() {

        context = LoginActivity.this;

        prefHelp = new PreferenceHelper(this);

        edt_login_mno = findViewById(R.id.edt_login_mno);
        edt_login_pass = findViewById(R.id.edt_login_pass);
        btn_login_forget_pass = findViewById(R.id.btn_login_forget_pass);
        btn_login_signin = findViewById(R.id.btn_login_signin);
        btn_login_signup = findViewById(R.id.btn_login_signup);
       /* final ProgressGenerator progressGenerator = new ProgressGenerator(this);
        final ActionProcessButton btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);
        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.getBoolean(EXTRAS_ENDLESS_MODE)) {
            btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        } else {
            btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
        }
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start(btnSignIn);
               // btnSignIn.setEnabled(false);
                //edt_login_mno.setEnabled(false);
                //edt_login_pass.setEnabled(false);
            }
        });*/
    }
/*
    @Override
    public void onComplete() {
        Toast.makeText(this,"Loading complete...", Toast.LENGTH_LONG).show();
    }*/
}
