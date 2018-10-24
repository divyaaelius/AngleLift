package com.angellift.Login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.angellift.MainActivity;
import com.angellift.R;
import com.angellift.utils.ConstMethod;
import com.angellift.utils.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;

public class LoginActivity extends AppCompatActivity  {

    Context context;
    EditText edt_login_mno, edt_login_pass;
    Button btn_login_signin;
    TextView btn_login_signup, btn_login_forget_pass;
    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        init();

        btn_login_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (ConstMethod.isInternetOn(LoginActivity.this)) {
                    if (TextUtils.isEmpty(edt_login_mno.getText())) {
                        edt_login_mno.requestFocus();
                        edt_login_mno.setError("Enter Mobile No.");

                    }else if (TextUtils.isEmpty(edt_login_pass.getText())) {
                        edt_login_pass.requestFocus();
                        edt_login_pass.setError("Enter Password");

                    } else if (ConstMethod.isValidPassword(edt_login_pass.getText().toString())) {*/
             getUserLogin();
/*
                    } else {
                        edt_login_pass.requestFocus();
                        edt_login_pass.setError("Enter Valid Password");
                    }
                } else {
                    ConstMethod.NetworkAlert(LoginActivity.this);

                }*/
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

        startActivity(new Intent(context, MainActivity.class));
    }

    private void init() {

        context = LoginActivity.this;

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
