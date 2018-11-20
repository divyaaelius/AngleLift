package com.angellift.settings;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.angellift.AsynceTask.ServiceAsync;
import com.angellift.MainActivity;
import com.angellift.R;
import com.angellift.utils.Const;
import com.angellift.utils.ConstMethod;
import com.angellift.utils.PreferenceHelper;

import static com.angellift.utils.Const.UrlClient.CHANGE_PASSWORD_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {

    EditText old_pass,new_pass,confm_new_pass;
    Button change_pass;
    String TAG="ChangePasswordFragment";
    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_change_password, container, false);

        init(v);

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation();
            }
        });

        return v;
    }

    private void Validation() {
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean fieldsOK = ConstMethod.validate(new EditText[]{old_pass, new_pass, confm_new_pass
                }, new String[]{"Enter Old Password","Enter New Password","Confirm New Password"});

                if(fieldsOK){
                    if(new_pass.getText().toString().trim().equals(confm_new_pass.getText().toString())){
                        ServiceCall();
                    }else {
                        confm_new_pass.setError("Password Not Match");
                    }
                }
            }
        });
    }
    private void ServiceCall() {
        final String oPass = old_pass.getText().toString().trim();
        final String nPass = new_pass.getText().toString().trim();
        final String cnPass = confm_new_pass.getText().toString().trim();


        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter(Const.Params.IDENTITY, new PreferenceHelper(getContext()).getUSER_NAME());
        builder.appendQueryParameter(Const.Params.OLD, oPass);
        builder.appendQueryParameter(Const.Params.NEW, nPass);
        builder.appendQueryParameter(Const.Params.NEW_CONFIRM, cnPass);
        builder.appendQueryParameter(Const.Params.USER_ID, new PreferenceHelper(getContext()).getUserId());

        Log.d(TAG, "builder ----------->" + builder);

        new ServiceAsync(CHANGE_PASSWORD_URL, new ServiceAsync.OnAsyncResult() {
            @Override
            public void onSuccess(String result) {

                Log.d(TAG, "----------->" + result);

                    Toast.makeText(getContext(), "Password Change successfully", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();

            }
            @Override
            public void onFailure(String result) {
                Log.d(TAG, "----------->" + result);
            }
        }, builder).execute();
    }
    private void init(View v) {

        old_pass=v.findViewById(R.id.old_pass);
        new_pass=v.findViewById(R.id.new_pass);
        confm_new_pass=v.findViewById(R.id.confm_new_pass);
        change_pass=v.findViewById(R.id.change_pass);
    }

}
