package com.angellift.settings;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.angellift.AsynceTask.ServiceAsync;
import com.angellift.AsynceTask.UploadImageAsync;
import com.angellift.R;
import com.angellift.utils.Const;
import com.angellift.utils.ConstMethod;
import com.angellift.utils.FilePath;
import com.angellift.utils.PreferenceHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.angellift.utils.Const.PICK_IMAGE_REQUEST;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    String TAG = "SignupActivity";
    private static final int STORAGE_PERMISSION_CODE = 101;
    EditText edt_signup_name, edt_signup_mno, edt_signup_email,
            edt_signup_address;
    Button btn_signup;
    TextView tv_dob_date;
    Context context;
    CircleImageView profile_image;
    private Uri filePath;
    String realFilePath;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        init(v);

        return v;

    }

    private void init(View v) {

        edt_signup_name = v.findViewById(R.id.prof_edt_name);
        edt_signup_mno = v.findViewById(R.id.prof_edt_mno);
        edt_signup_email = v.findViewById(R.id.prof_edt_email);
        edt_signup_address = v.findViewById(R.id.prof_edt_address);
        btn_signup = v.findViewById(R.id.prof_btn);
        tv_dob_date = v.findViewById(R.id.prof_tv_dob_date);
        profile_image = v.findViewById(R.id.prof_profile_image);

        getData();

        tv_dob_date.setOnClickListener(this);
        profile_image.setOnClickListener(this);
        btn_signup.setOnClickListener(this);
    }

    private void getData() {

        final ProgressDialog myDialog = ConstMethod.showProgressDialog(getActivity(), getResources().getString(R.string.please_wait));

        String id = new PreferenceHelper(getContext()).getUSER_DETAILS_ID();
        Uri.Builder builder = new Uri.Builder();

        Log.e("Login url ", Const.UrlClient.GET_PROFILE_DATA_URL);

        new ServiceAsync(Const.UrlClient.GET_PROFILE_DATA_URL + id, new ServiceAsync.OnAsyncResult() {
            @Override
            public void onSuccess(String result) {
                Log.e("JSON", "DATA " + result);
                myDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString(Const.Params.STATUS).equals(Const.Params.TRUE)) {
                        //String str = jsonObject.getString("status");
                        JSONObject object = jsonObject.getJSONObject(Const.Params.PROFILEDETAILS);

                        edt_signup_name.setText(object.getString(Const.Params.C_NAME));
                        edt_signup_mno.setText(object.getString(Const.Params.C_MOBILENO));
                        edt_signup_address.setText(object.getString(Const.Params.C_ADDRESS));
                        tv_dob_date.setText(object.getString(Const.Params.C_DOB));
                        edt_signup_email.setText(new PreferenceHelper(getContext()).getEmail());

                        Picasso.with(getContext()).load(Const.Params.C_IMAGE)
                                .placeholder(R.drawable.angel_bg)
                                .error(R.drawable.angel_icon)
                                .into(profile_image);

                    } else {
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

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.prof_tv_dob_date) {
            ConstMethod.dateForamtYYYMMDDWithDesh(getContext(), tv_dob_date);

        }
        if (v.getId() == R.id.prof_btn) {
            ServiceCall();
        }
        if (v.getId() == R.id.prof_profile_image) {
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            } else {
                SelectFile();

            }
        }
    }
    public void SelectFile() {

        String[] mimeTypes = {"image/*"};

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
        startActivityForResult(Intent.createChooser(intent, "Select"), PICK_IMAGE_REQUEST);

    }
    private void ServiceCall() {
        String id = new PreferenceHelper(getContext()).getUSER_DETAILS_ID();
        final ProgressDialog myDialog = ConstMethod.showProgressDialog(getContext(), getResources().getString(R.string.please_wait));

        HashMap<String, String> hashMap = new HashMap<>();
        HashMap<String, File> hashMap1 = new HashMap<>();

        hashMap.put(Const.Params.C_ID, id);
        hashMap.put(Const.Params.C_ADDRESS, edt_signup_address.getText().toString());
        hashMap.put(Const.Params.C_DOB, tv_dob_date.getText().toString());

        hashMap1.put(Const.Params.C_IMAGE, new File(realFilePath));

        ConstMethod.LodDebug(TAG, "url --->" + Const.UrlClient.SET_PROFILE_DATA_URL + id);
        ConstMethod.LodDebug(TAG, "text hashMap --->" + hashMap.toString());
        ConstMethod.LodDebug(TAG, "file path hashMap1 --->" + hashMap1.toString());

        UploadImageAsync imageAsync = new UploadImageAsync(Const.UrlClient.SET_PROFILE_DATA_URL + id, hashMap, hashMap1, new UploadImageAsync.OnAsyncResult() {
            @Override
            public void onSuccess(String result) {
                myDialog.dismiss();
                ConstMethod.LodDebug(TAG, "Result--->" + result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString(Const.Params.STATUS).equals(Const.Params.TRUE)) {
                        if (jsonObject.getString(Const.Params.PROFILE_UPDATE).equals(Const.Params.TRUE)) {
                            ConstMethod.showToast(getContext(), "profile update");
                        }

                    }
                } catch (Exception e) {
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


    }

    // permission for access gallary
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        ConstMethod.SelectFile(getActivity());
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
            realFilePath = FilePath.getPath(getActivity(), filePath);
            ConstMethod.LodDebug("real path", "------------>" + realFilePath);
            try {
                Picasso.with(getContext())
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
