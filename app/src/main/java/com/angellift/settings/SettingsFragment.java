package com.angellift.settings;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.angellift.R;
import com.angellift.home.adapter.PlacesAutoCompleteAdapter;
import com.angellift.utils.PreferenceHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener {
    private PlacesAutoCompleteAdapter  adapterHomeAddress, adapterWorkAddress;
    AutoCompleteTextView etHomeAddress, etWorkAddress;
    private LinearLayout layoutHomeText, layoutHomeEdit, layoutWorkText,
            layoutWorkEdit, sendReqLayout, linearPickupAddress, vehicleLayout;

    TextView tvHomeAddress, tvWorkAddress;
    private ListView nearByList;
    private PreferenceHelper preference;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_settings, container, false);
        initId(view);
        return view;
    }

    private void initId(View view ) {

        preference = new PreferenceHelper(getActivity());
        etHomeAddress =view.findViewById(R.id.etHomeAddress);
        etWorkAddress = view.findViewById(R.id.etWorkAddress);
        tvHomeAddress =view
                .findViewById(R.id.tvHomeAddress);
        tvWorkAddress = view
                .findViewById(R.id.tvWorkAddress);

        if (preference.getHomeAddress() != null) {
            tvHomeAddress.setText(preference.getHomeAddress());
            etHomeAddress.setText(preference.getHomeAddress());
        }
        if (preference.getWorkAddress() != null) {
            tvWorkAddress.setText(preference.getWorkAddress());
            etWorkAddress.setText(preference.getWorkAddress());
        }

        layoutHomeText =view
                .findViewById(R.id.layoutHomeText);
        layoutHomeEdit =view
                .findViewById(R.id.layoutHomeEdit);
        layoutWorkText =view
                .findViewById(R.id.layoutWorkText);
        layoutWorkEdit =view
                .findViewById(R.id.layoutWorkEdit);
        layoutHomeText.setOnClickListener(this);
        layoutWorkText.setOnClickListener(this);


        view.findViewById(R.id.imgClearHome).setOnClickListener(
                this);
        view.findViewById(R.id.imgClearWork).setOnClickListener(
                this);

        view.findViewById(R.id.btnEditWork).setOnClickListener(
                this);
        view.findViewById(R.id.btnEditHome).setOnClickListener(
                this);
        nearByList = view.findViewById(R.id.nearByList);
      //  pbNearby = view.findViewById(R.id.pbNearby);


       // nearByLocations();
        nearByList.setOnItemClickListener(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        adapterHomeAddress = new PlacesAutoCompleteAdapter(getActivity(),
                R.layout.autocomplete_list_text);
        etHomeAddress.setAdapter(adapterHomeAddress);
        adapterWorkAddress = new PlacesAutoCompleteAdapter(getActivity(),
                R.layout.autocomplete_list_text);
        etWorkAddress.setAdapter(adapterWorkAddress);

        etHomeAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String selectedPlace = adapterHomeAddress.getItem(arg2);
                closeKeyboard(etHomeAddress);
                preference.putHomeAddress(selectedPlace);
                tvHomeAddress.setText(selectedPlace);
                layoutHomeEdit.setVisibility(LinearLayout.GONE);
                layoutHomeText.setVisibility(LinearLayout.VISIBLE);
            }
        });
        etWorkAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String selectedPlace = adapterWorkAddress.getItem(arg2);
                closeKeyboard(etWorkAddress);
                 preference.putWorkAddress(selectedPlace);
                tvWorkAddress.setText(selectedPlace);
                layoutWorkEdit.setVisibility(LinearLayout.GONE);
                layoutWorkText.setVisibility(LinearLayout.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditHome:
                layoutHomeEdit.setVisibility(LinearLayout.VISIBLE);
                layoutHomeText.setVisibility(LinearLayout.GONE);
                break;
            case R.id.btnEditWork:
                layoutWorkEdit.setVisibility(LinearLayout.VISIBLE);
                layoutWorkText.setVisibility(LinearLayout.GONE);
                break;

            case R.id.imgClearHome:
                etHomeAddress.setText("");
                break;
            case R.id.imgClearWork:
                etWorkAddress.setText("");
                break;
        }

    }
    private void closeKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                .getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
