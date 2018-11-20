package com.angellift.Login;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.angellift.AsynceTask.UploadImageAsync;
import com.angellift.MainActivity;
import com.angellift.R;
import com.angellift.invoice.InvoiceFragment;
import com.angellift.utils.Const;
import com.angellift.utils.ConstMethod;
import com.angellift.utils.FilePath;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.angellift.utils.Const.PICK_IMAGE_REQUEST;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG="SignupActivity";
    private static final int STORAGE_PERMISSION_CODE = 101;
    EditText edt_signup_name, edt_signup_mno, edt_signup_email, edt_signup_pass,
            edt_signup_address,edt_signup_pass_conf;
    Button btn_signup;
    TextView tv_dob_date;
    Context context;
    TextView profile_image;
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


             //   Validation();

    }

    private void Validation() {

        if (ConstMethod.isInternetOn(SignupActivity.this)) {
            if (edt_signup_name.getText().toString().isEmpty()) {
                edt_signup_name.requestFocus();
                //edt_signup_name.setError("Enter Name");

            } else if (TextUtils.isEmpty(edt_signup_mno.getText().toString())) {
                edt_signup_mno.requestFocus();
               // edt_signup_mno.setError("Enter Mobile No.");

            } else if (TextUtils.isEmpty(edt_signup_email.getText().toString())) {
                edt_signup_email.requestFocus();
               // edt_signup_email.setError("Enter Email ID");

            } else if (!ConstMethod.isValidMail(edt_signup_email.getText().toString())) {
                edt_signup_email.requestFocus();
                //edt_signup_email.setError("Enter Valid Email");

            }  else if (TextUtils.isEmpty(tv_dob_date.getText().toString())) {
                tv_dob_date.requestFocus();
              //  tv_dob_date.setError("Select Date");

            }else if (TextUtils.isEmpty(edt_signup_address.getText().toString())) {
                edt_signup_address.requestFocus();
                //edt_signup_address.setError("Enter Address");

            } else if (TextUtils.isEmpty(edt_signup_pass.getText().toString())) {
                edt_signup_pass.requestFocus();
               // edt_signup_pass.setError("Enter Password");

            } else if (TextUtils.isEmpty(edt_signup_pass_conf.getText().toString())) {
                edt_signup_pass_conf.requestFocus();
                //edt_signup_pass_conf.setError("Enter Conform Password");

            } else if (ConstMethod.isValidPassword(edt_signup_pass_conf.getText().toString())) {
                if (edt_signup_pass.getText().toString().equals(
                        edt_signup_pass_conf.getText().toString())) {




                } else {
                    edt_signup_pass_conf.requestFocus();
                   // edt_signup_pass_conf.setError("Password Miss-Match");
                }


            } else {
                edt_signup_pass_conf.requestFocus();
               // edt_signup_pass_conf.setError("Enter Valid Password");
            }
        } else {
            ConstMethod.NetworkAlert(SignupActivity.this);

        }
    }

    private void SinupCalling() {

        final ProgressDialog myDialog = ConstMethod.showProgressDialog(this, getResources().getString(R.string.please_wait));

        HashMap<String, String> hashMap = new HashMap<>();
        HashMap<String, File> hashMap1 = new HashMap<>();

        hashMap.put(Const.Params.C_MOBILENO,edt_signup_mno.getText().toString() );
        hashMap.put(Const.Params.C_NAME, edt_signup_name.getText().toString());
        hashMap.put(Const.Params.C_ADDRESS, "");
        hashMap.put(Const.Params.C_DOB, tv_dob_date.getText().toString());
        hashMap.put(Const.Params.PASSWORD, edt_signup_pass_conf.getText().toString());
        hashMap.put(Const.Params.MOBILE_TYPE, String.valueOf(Const.MOBILETYPE));

        hashMap1.put(Const.Params.C_IMAGE, new File(realFilePath));


        ConstMethod.LodDebug(TAG, "url --->" + Const.UrlClient.REGISTER_URL);
        ConstMethod.LodDebug(TAG, "text hashMap --->" + hashMap.toString());
        ConstMethod.LodDebug(TAG, "file path hashMap1 --->" + hashMap1.toString());

        UploadImageAsync imageAsync = new UploadImageAsync(Const.UrlClient.REGISTER_URL, hashMap, hashMap1, new UploadImageAsync.OnAsyncResult() {
            @Override
            public void onSuccess(String result) {
                myDialog.dismiss();
                ConstMethod.LodDebug(TAG, "Result--->" + result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString(Const.Params.STATUS).equals(Const.Params.TRUE)) {

                        ConstMethod.showToast(context, "Request Submit success");
                        replaceFragment();
                    }
                }  catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String result) {
                myDialog.dismiss();
                ConstMethod.LodDebug(TAG, "Result failed--->" + result);

            }
        });
        imageAsync.execute();


        //replaceFragment();
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

        context=SignupActivity.this;

        edt_signup_name = findViewById(R.id.edt_signup_name);
        edt_signup_mno = findViewById(R.id.edt_signup_mno);
        edt_signup_email = findViewById(R.id.edt_signup_email);
        edt_signup_pass = findViewById(R.id.edt_signup_pass);
      //  edt_signup_address = findViewById(R.id.edt_signup_address);
        edt_signup_pass_conf = findViewById(R.id.edt_signup_pass_conf);
        btn_signup = findViewById(R.id.btn_signup);
        tv_dob_date = findViewById(R.id.tv_dob_date);
        tv_dob_date.setOnClickListener(this);
    /*    profile_image = findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);*/
        btn_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_dob_date) {
            ConstMethod.dateForamtYYYMMDDWithDesh(this, tv_dob_date);

        }if (v.getId() == R.id.btn_signup) {
            SinupCalling();
         // Validation();
        }
       /* if (v.getId() == R.id.profile_image) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            } else {
                ConstMethod.SelectFile(this);

            }
        }*/
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
               /* Picasso.with(this)
                        .load(filePath)
                        .error(R.drawable.icon_trans)
                        .placeholder(R.drawable.icon_trans)
                        .into(profile_image);*/
             //  profile_image.setText(realFilePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
