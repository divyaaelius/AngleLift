package com.angellift.Login;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.angellift.MainActivity;
import com.angellift.R;
import com.angellift.invoice.InvoiceFragment;
import com.angellift.utils.ConstMethod;
import com.angellift.utils.FilePath;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.angellift.utils.Const.PICK_IMAGE_REQUEST;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int STORAGE_PERMISSION_CODE = 101;
    EditText edt_signup_name, edt_signup_mno, edt_signup_email, edt_signup_pass, edt_signup_pass_conf;
    Button btn_signup;
    TextView tv_dob_date;

    CircleImageView profile_image;
    private Uri filePath;
    String realFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        init();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
                //Validation();
            }
        });
    }

    private void Validation() {

        if (ConstMethod.isInternetOn(SignupActivity.this)) {
            if (TextUtils.isEmpty(edt_signup_name.getText().toString())) {
                edt_signup_name.setError(" hi ");
                edt_signup_name.requestFocus();
                //  edt_signup_name.setError("Enter Name");

            } else if (TextUtils.isEmpty(edt_signup_mno.getText().toString())) {
                edt_signup_mno.requestFocus();
                //   edt_signup_mno.setError("Enter Mobile No.");

            } else if (TextUtils.isEmpty(edt_signup_email.getText().toString())) {
                edt_signup_email.requestFocus();
                //  edt_signup_email.setError("Enter Email ID");

            } else if (!ConstMethod.isValidMail(edt_signup_email.getText().toString())) {
                edt_signup_email.requestFocus();
                //   edt_signup_email.setError("Enter Valid Email");

            } else if (TextUtils.isEmpty(edt_signup_pass.getText().toString())) {
                edt_signup_pass.requestFocus();

                //  edt_signup_pass.setError("Enter Password");

            } else if (TextUtils.isEmpty(edt_signup_pass_conf.getText().toString())) {
                edt_signup_pass_conf.requestFocus();
                //  edt_signup_pass_conf.setError("Enter Conform Password");

            } else if (ConstMethod.isValidPassword(edt_signup_pass_conf.getText().toString())) {
                if (edt_signup_pass.getText().toString().equals(
                        edt_signup_pass_conf.getText().toString())) {
                    SinupCalling();
                } else {
                    edt_signup_pass_conf.requestFocus();
                    edt_signup_pass_conf.setError("Password Miss-Match");
                }


            } else {
                edt_signup_pass_conf.requestFocus();
                edt_signup_pass_conf.setError("Enter Valid Password");
            }
        } else {
            ConstMethod.NetworkAlert(SignupActivity.this);

        }
    }

    private void SinupCalling() {

        replaceFragment();
        //  startActivity(new Intent(SignupActivity.this, MainActivity.class));
    }


    public void replaceFragment() {
        OtpFragment inFrag = new OtpFragment();
        FragmentManager fragmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_otp, inFrag, "");
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void init() {

        edt_signup_name = findViewById(R.id.edt_signup_name);
        edt_signup_mno = findViewById(R.id.edt_signup_mno);
        edt_signup_email = findViewById(R.id.edt_signup_email);
        edt_signup_pass = findViewById(R.id.edt_signup_pass);
        edt_signup_pass_conf = findViewById(R.id.edt_signup_pass_conf);
        btn_signup = findViewById(R.id.btn_signup);
        tv_dob_date = findViewById(R.id.tv_dob_date);
        tv_dob_date.setOnClickListener(this);
        profile_image = findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_dob_date) {
            ConstMethod.dateForamtYYYMMDDWithDesh(this, tv_dob_date);

        }
        if (v.getId() == R.id.profile_image) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            } else {
                ConstMethod.SelectFile(this);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        ConstMethod.SelectFile(this);
                    }
                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            ConstMethod.LodDebug("File path", "------------>" + filePath);
            realFilePath = FilePath.getPath(this, filePath);
            ConstMethod.LodDebug("real path", "------------>" + realFilePath);
            try {
                Picasso.with(this)
                        .load(filePath)
                        .error(R.drawable.icon_trans)
                        .placeholder(R.drawable.icon_trans)
                        .into(profile_image);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
