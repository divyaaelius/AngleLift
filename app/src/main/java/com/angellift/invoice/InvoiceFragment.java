package com.angellift.invoice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.angellift.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InvoiceFragment extends Fragment {

    TextView  txt_invoice_pickup_loc,txt_invoice_total_rs,txt_invoice_total_km,txt_invoice_drop_loc,
            txt_invoice_trip_cost,txt_invoice_trip_discount,txt_invoice_trip_total;

    Button btn_invoice_payment;

    public InvoiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_invoice, container, false);
        init(v);
        return v;
    }

    private void init(View v) {

        txt_invoice_pickup_loc=v.findViewById(R.id.txt_invoice_pickup_loc);
        txt_invoice_total_rs=v.findViewById(R.id.txt_invoice_total_rs);
        txt_invoice_total_km=v.findViewById(R.id.txt_invoice_total_km);
        txt_invoice_drop_loc=v.findViewById(R.id.txt_invoice_drop_loc);
        txt_invoice_trip_cost=v.findViewById(R.id.txt_invoice_trip_cost);
        txt_invoice_trip_discount=v.findViewById(R.id.txt_invoice_trip_discount);
        txt_invoice_trip_total=v.findViewById(R.id.txt_invoice_trip_total);
        btn_invoice_payment=v.findViewById(R.id.btn_invoice_payment);

    }

}
