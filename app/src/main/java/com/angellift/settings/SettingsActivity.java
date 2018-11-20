package com.angellift.settings;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.angellift.R;

public class SettingsActivity extends AppCompatActivity  {

    String TAG="SettingsActivity";
    Context context;
    TextView changePass,profile_setting,Favadd_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        init();
    }

    private void init() {

        context=SettingsActivity.this;

        changePass=findViewById(R.id.changePass);
        profile_setting=findViewById(R.id.profile_setting);
        Favadd_setting=findViewById(R.id.Favadd_setting);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordFragment fragment=new ChangePasswordFragment();
                replaceFragment(fragment);
            }
        });

        profile_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment fragment=new ProfileFragment();
                replaceFragment(fragment);
            }
        });
        Favadd_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment fragment=new SettingsFragment();
            }
        });
    }
    public void replaceFragment( Fragment fragTmp) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.contain_setting, fragTmp, "").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

}
