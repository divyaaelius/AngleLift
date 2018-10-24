package com.angellift.job;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.angellift.R;
import com.angellift.invoice.InvoiceFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobFragment extends Fragment {

    CircleImageView tvCallDriver, img_driver;
    TextView tv_driver_name, pickup_km, tv_car_details, tv_cancel_request;
    int status = 0;

    public JobFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_job, container, false);

        initId(v);  // fetch all id at here

        tvCallDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+919106839608"));

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},123);
                }
                else
                {
                    startActivity(callIntent);
                }*/
            }

        });
        tv_cancel_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvoiceFragment inFrag = new InvoiceFragment();
                FragmentManager fragmanager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, inFrag, "").addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });

        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 123: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+919086839608"));
                    startActivity(intent);
                } else {
                }
                return;
            }
        }
    }

    private void initId(View v) {
        tvCallDriver = v.findViewById(R.id.tv_call_driver);
        img_driver = v.findViewById(R.id.img_driver);
        tv_driver_name = v.findViewById(R.id.tv_driver_name);
        pickup_km = v.findViewById(R.id.pickup_km);
        tv_car_details = v.findViewById(R.id.tv_car_details);
        tv_cancel_request = v.findViewById(R.id.tv_cancel_request);

    }

}
