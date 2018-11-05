package com.angellift.Login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.angellift.R;
import com.angellift.utils.ConstMethod;
import com.angellift.utils.PreferenceHelper;

import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class OtpFragment extends Fragment {

    Button otp_submit, otp_resend;
    EditText edt_otp_no;
    public static String otpnum = "";
    PreferenceHelper pHelper;

    public OtpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_otp, container, false);

        initId(v);
        otp_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckValidation();

                //startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        otp_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otpnum = ConstMethod.random(4);
                pHelper.setOtpnumber(otpnum);

                setOtp("87979454", "Your New OTP is " + otpnum);

            }
        });

        return v;
    }

    private void initId(View v) {

        edt_otp_no = v.findViewById(R.id.edt_otp_no);
        otp_submit = v.findViewById(R.id.otp_submit);
        otp_resend = v.findViewById(R.id.otp_resend);
        pHelper = new PreferenceHelper(getActivity());

    }

    private void setOtp(String mobile, String message) {
        //  showProgressDialog();
        //apikey= 0845633d-3860-484c-bc98-43c82746254c  sendername: SSINFO

/*        public static final String URL_SMS = "http://sms.hspsms.com/sendSMS?";
        public static final String URL_SMS_API_KEY= "55501e7a-2617-4f6a-859d-8639935e80b8";
        public static final String URL_SMS_USERNAME = "aeditsoft";
        public static final String URL_SMS_SENDERNAME = "AEJEWL";*/

        //http://sms.hspsms.com/sendSMS?username=suhradamsoft&message=XXXXXXXXXX&sendername=SSINFO&smstype=TRANS&numbers=<mobile_numbers>&apikey=0845633d-3860-484c-bc98-43c82746254
        try {
            String query = URLEncoder.encode(message, "utf-8");
            // String smsUrl = Urls.URL_SMS + "username="+Urls.URL_SMS_USERNAME+"&message=" + query + "&sendername="+Urls.URL_SMS_SENDERNAME+"&smstype=TRANS&numbers=" + mobile + "&apikey="+Urls.URL_SMS_API_KEY;
            //    Log.d("Error OtpActivity: ", "**** smsUrl  " + smsUrl);
          /*  StringRequest strReq = new StringRequest(Request.Method.GET,
                    smsUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.d("OtpActivity:::", "***** setOtp" + response);
                        JSONArray arraJson=new JSONArray(response);
                        JSONObject jObg=arraJson.getJSONObject(0);
                        if (jObg.getString("responseCode").contains("Message SuccessFully Submitted"))
                        {

                            Toast.makeText(OtpActivity.this,jObg.getString("responseCode"),Toast.LENGTH_SHORT).show();
                        }
                    //    hideProgressDialog();

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    //parseJson(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("response", "Error OtpActivity: " + error.toString());
                    hideProgressDialog();
                }
            });*/
            //  AppController.getInstance().addToRequestQueue(strReq);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void CheckValidation() {
        if (!validateOTP()) {
            return;
        }
        Verify();
    }
    private void Verify() {
        if (pHelper.getOtpNumber().trim().equals(edt_otp_no.getText().toString().trim())){
           pHelper.setOtpnumber("");
          //  editor.putBoolean(Constants.IS_OTP_VERIFIED, true);
            edt_otp_no.setText("");
            edt_otp_no.setError(null);
            // startMainActivity();
          /*  if (loginType.equals("1")){
                loginUser();
            }else {
                signupUser();
            }*/

        }else{
            edt_otp_no.setError("Wrong OTP");
        }
    }
    private boolean validateOTP() {
        if (edt_otp_no.getText().toString().trim().isEmpty()) {
            edt_otp_no.setError("Enter OTP ");
            edt_otp_no.requestFocus();
            return false;
        } else {
            edt_otp_no.setError(null);
        }
        return true;
    }
}
