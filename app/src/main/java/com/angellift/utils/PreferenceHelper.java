package com.angellift.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.android.gms.maps.model.LatLng;

public class PreferenceHelper {

    private SharedPreferences app_prefs;
    private final String USER_ID = "user_id";
    private final String EMAIL = "email";
    private final String PASSWORD = "password";
    private final String DEVICE_TOKEN = "device_token";
    private final String SESSION_TOKEN = "session_token";
    private final String REQUEST_ID = "request_id";
    private final String REQUEST_TIME = "request_time";
    private final String REQUEST_LATITUDE = "request_latitude";
    private final String REQUEST_LONGITUDE = "request_longitude";
    private final String LOGIN_BY = "login_by";
    private final String SOCIAL_ID = "social_id";
    private final String PAYMENT_MODE = "payment_mode";
    private final String DEFAULT_CARD = "default_card";
    private final String DEFAULT_CARD_NO = "default_card_no";
    private final String DEFAULT_CARD_TYPE = "default_card_type";
    private final String BASE_PRICE = "base_cost";
    private final String DISTANCE_PRICE = "distance_cost";
    private final String TIME_PRICE = "time_cost";
    private final String IS_TRIP_STARTED = "is_trip_started";
    private final String HOME_ADDRESS = "home_address";
    private final String WORK_ADDRESS = "work_address";
    private final String DEST_LAT = "dest_lat";
    private final String DEST_LNG = "dest_lng";
    private final String REFEREE = "is_referee";
    private final String PROMO_CODE = "promo_code";
    //	private Context context;
    private final String TIME_ZONE = "time_zone";
    private final String START_TIME = "start_time";
    private final String CURRENT_TRIP_TIME = "time";
    private final String STRIPE_PUBLISHABLE_KEY = "stripe_publishable_key";
    private final String BROWSER_KEY = "browser_key";
    private final String OTP_NUMBER = "otp_number";

    private final String CREATEDBYUSERID = "c_createbyuserid";
    private final String USER_NAME = "username";
    private final String ISLOGIN = "isLogin";
    private final String TOKEN = "token_id";
    private final String FIRST_NAME = "first_name";
    private final String USER_DETAILS_ID = "c_userdetails_id";
    private final String PROFILE_IMAGE = "c_image";

    private final String ROLE_ID = "role_id";


    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences(Const.PREF_NAME,
                Context.MODE_PRIVATE);
//		this.context = context;
    }

    public String getROLE_ID() {
        return app_prefs.getString(ROLE_ID, null);
    }

    public void putROLE_ID(String role_id) {
        Editor edit = app_prefs.edit();
        edit.putString(ROLE_ID, role_id);
        edit.commit();
    }

    public String getCREATEDBYUSERID() {
        return  app_prefs.getString(CREATEDBYUSERID, null);
    }

    public void putCreatedByUserId(String createdByUSerID) {
        Editor edit = app_prefs.edit();
        edit.putString(CREATEDBYUSERID, createdByUSerID);
        edit.commit();
    }

    public String getUSER_NAME() {
        return app_prefs.getString(USER_NAME, null);
    }

    public void putUsername(String username) {
        Editor edit = app_prefs.edit();
        edit.putString(USER_NAME, username);
        edit.commit();
    }

    public boolean getLogin() {
        return app_prefs.getBoolean(ISLOGIN, false);

    }

    public void putLogin(boolean login) {
        Editor edit = app_prefs.edit();
        edit.putBoolean(ISLOGIN, login);
        edit.commit();
    }


    public String getTOKEN() {
        return TOKEN;
    }

    public void putTOKEN(String token) {
        Editor edit = app_prefs.edit();
        edit.putString(TOKEN, token);
        edit.commit();
    }

    public String getFIRST_NAME() {
        return  app_prefs.getString(FIRST_NAME, null);
    }

    public void putFIRST_NAME(String first_name) {
        Editor edit = app_prefs.edit();
        edit.putString(FIRST_NAME, first_name);
        edit.commit();
    }

    public String getUSER_DETAILS_ID() {
        return  app_prefs.getString(USER_DETAILS_ID, null);
    }

    public void putUserDetailId(String user_details_id) {
        Editor edit = app_prefs.edit();
        edit.putString(USER_DETAILS_ID, user_details_id);
        edit.commit();
    }

    public String getPROFILE_IMAGE() {
        return PROFILE_IMAGE;
    }


    public void setOtpnumber(String otpnumber) {
        Editor edit = app_prefs.edit();
        edit.putString(OTP_NUMBER, otpnumber);
        edit.commit();
    }

    public String getOtpNumber() {
        return app_prefs.getString(OTP_NUMBER, "");
    }

    public void putStripePublishableKey(String email) {
        Editor edit = app_prefs.edit();
        edit.putString(STRIPE_PUBLISHABLE_KEY, email);
        edit.commit();
    }

    public String getStripePublishableKey() {
        return app_prefs.getString(STRIPE_PUBLISHABLE_KEY, null);
    }

    public void putBrowserKey(String email) {
        Editor edit = app_prefs.edit();
        edit.putString(BROWSER_KEY, email);
        edit.commit();
    }




    public void putEmail(String email) {
        Editor edit = app_prefs.edit();
        edit.putString(EMAIL, email);
        edit.commit();
    }

    public String getEmail() {
        return app_prefs.getString(EMAIL, null);
    }

    public void putPassword(String password) {
        Editor edit = app_prefs.edit();
        edit.putString(PASSWORD, password);
        edit.commit();
    }

    public String getPassword() {
        return app_prefs.getString(PASSWORD, null);
    }

    public void putBasePrice(float price) {
        Editor edit = app_prefs.edit();
        edit.putFloat(BASE_PRICE, price);
        edit.commit();
    }

    public String getBrowserKey() {
        return app_prefs.getString(BROWSER_KEY, null);
    }

    public float getBasePrice() {
        return app_prefs.getFloat(BASE_PRICE, 0f);
    }

    public void putDistancePrice(float price) {
        Editor edit = app_prefs.edit();
        edit.putFloat(DISTANCE_PRICE, price);
        edit.commit();
    }

    public float getDistancePrice() {
        return app_prefs.getFloat(DISTANCE_PRICE, 0f);
    }

    public void putTimePrice(float price) {
        Editor edit = app_prefs.edit();
        edit.putFloat(TIME_PRICE, price);
        edit.commit();
    }

    public float getTimePrice() {
        return app_prefs.getFloat(TIME_PRICE, 0f);
    }

    public void putSocialId(String id) {
        Editor edit = app_prefs.edit();
        edit.putString(SOCIAL_ID, id);
        edit.commit();
    }

    public String getSocialId() {
        return app_prefs.getString(SOCIAL_ID, null);
    }

    public String getUserId() {
        return app_prefs.getString(USER_ID, null);

    }
    public void putUserId(String userId) {
        Editor edit = app_prefs.edit();
        edit.putString(USER_ID, userId);
        edit.commit();
    }

    public void putDeviceToken(String deviceToken) {
        Editor edit = app_prefs.edit();
        edit.putString(DEVICE_TOKEN, deviceToken);
        edit.commit();
    }

    public String getDeviceToken() {
        return app_prefs.getString(DEVICE_TOKEN, null);

    }

    public void putSessionToken(String sessionToken) {
        Editor edit = app_prefs.edit();
        edit.putString(SESSION_TOKEN, sessionToken);
        edit.commit();
    }

    public String getSessionToken() {
        return app_prefs.getString(SESSION_TOKEN, null);

    }

    public void putRequestId(int requestId) {
        Editor edit = app_prefs.edit();
        edit.putInt(REQUEST_ID, requestId);
        edit.commit();
    }

    public int getRequestId() {
        return app_prefs.getInt(REQUEST_ID, Const.NO_REQUEST);
    }

    public void putLoginBy(String loginBy) {
        Editor edit = app_prefs.edit();
        edit.putString(LOGIN_BY, loginBy);
        edit.commit();
    }

    public String getLoginBy() {
        return app_prefs.getString(LOGIN_BY, Const.MANUAL);
    }

    public void putRequestTime(long time) {
        Editor edit = app_prefs.edit();
        edit.putLong(REQUEST_TIME, time);
        edit.commit();
    }

    public long getRequestTime() {
        return app_prefs.getLong(REQUEST_TIME, Const.NO_TIME);
    }

    public void putPaymentMode(int mode) {
        Editor edit = app_prefs.edit();
        edit.putInt(PAYMENT_MODE, mode);
        edit.commit();
    }

    public int getPaymentMode() {
        return app_prefs.getInt(PAYMENT_MODE, Const.CASH);
    }

    public void putDefaultCard(int cardId) {
        Editor edit = app_prefs.edit();
        edit.putInt(DEFAULT_CARD, cardId);
        edit.commit();
    }

    public int getDefaultCard() {
        return app_prefs.getInt(DEFAULT_CARD, 0);
    }

    public void putDefaultCardNo(String cardNo) {
        Editor edit = app_prefs.edit();
        edit.putString(DEFAULT_CARD_NO, cardNo);
        edit.commit();
    }

    public String getDefaultCardNo() {
        return app_prefs.getString(DEFAULT_CARD_NO, "");
    }

    public void putDefaultCardType(String cardType) {
        Editor edit = app_prefs.edit();
        edit.putString(DEFAULT_CARD_TYPE, cardType);
        edit.commit();
    }

    public String getDefaultCardType() {
        return app_prefs.getString(DEFAULT_CARD_TYPE, "");
    }

    public boolean getIsTripStarted() {
        return app_prefs.getBoolean(IS_TRIP_STARTED, false);
    }

    public void putIsTripStarted(boolean started) {
        Editor edit = app_prefs.edit();
        edit.putBoolean(IS_TRIP_STARTED, started);
        edit.commit();
    }

    public String getHomeAddress() {
        return app_prefs.getString(HOME_ADDRESS, null);
    }

    public void putHomeAddress(String homeAddress) {
        Editor edit = app_prefs.edit();
        edit.putString(HOME_ADDRESS, homeAddress);
        edit.commit();
    }

    public String getWorkAddress() {
        return app_prefs.getString(WORK_ADDRESS, null);
    }

    public void putWorkAddress(String homeAddress) {
        Editor edit = app_prefs.edit();
        edit.putString(WORK_ADDRESS, homeAddress);
        edit.commit();
    }

    public void putRequestLocation(LatLng latLang) {
        Editor edit = app_prefs.edit();
        edit.putString(REQUEST_LATITUDE, String.valueOf(latLang.latitude));
        edit.putString(REQUEST_LONGITUDE, String.valueOf(latLang.longitude));
        edit.commit();
    }

    public LatLng getRequestLocation() {
        LatLng latLng = new LatLng(0.0, 0.0);
        try {
            latLng = new LatLng(Double.parseDouble(app_prefs.getString(
                    REQUEST_LATITUDE, "0.0")), Double.parseDouble(app_prefs
                    .getString(REQUEST_LONGITUDE, "0.0")));
        } catch (NumberFormatException nfe) {
            latLng = new LatLng(0.0, 0.0);
        }
        return latLng;
    }

    public void putClientDestination(LatLng destination) {
        Editor edit = app_prefs.edit();
        if (destination == null) {
            edit.putString(DEST_LAT, null);
            edit.putString(DEST_LNG, null);
        } else {
            edit.putString(DEST_LAT, String.valueOf(destination.latitude));
            edit.putString(DEST_LNG, String.valueOf(destination.longitude));
        }
        edit.commit();
    }

    public LatLng getClientDestination() {
        try {
            if (app_prefs.getString(DEST_LAT, null) != null) {
                return new LatLng(Double.parseDouble(app_prefs.getString(
                        DEST_LAT, "0.0")), Double.parseDouble(app_prefs
                        .getString(DEST_LNG, "0.0")));
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void putReferee(int is_referee) {
        Editor edit = app_prefs.edit();
        edit.putInt(REFEREE, is_referee);
        edit.commit();
    }

    public int getReferee() {
        return app_prefs.getInt(REFEREE, 0);
    }

    public void putPromoCode(String promoCode) {
        Editor edit = app_prefs.edit();
        edit.putString(PROMO_CODE, promoCode);
        edit.commit();
    }

    public String getPromoCode() {
        return app_prefs.getString(PROMO_CODE, null);
    }

    public void clearRequestData() {
      /*  putRequestId(Const.NO_REQUEST);
        putRequestTime(Const.NO_TIME);
        putRequestLocation(new LatLng(0.0, 0.0));
        putIsTripStarted(false);
        putClientDestination(null);
        putPromoCode(null);*/
        // new DBHelper(context).deleteAllLocations();
    }

    public void Logout() {
        clearRequestData();
        // new DBHelper(context).deleteUser();
        putUserId(null);
        putSessionToken(null);
        putSocialId(null);
        putClientDestination(null);
        //    putLoginBy(Const.MANUAL);
        app_prefs.edit().clear();
    }

    public void putTimeZone(String timeZone) {
        Editor edit = app_prefs.edit();
        edit.putString(TIME_ZONE, timeZone);
        edit.commit();
    }

    public String getTimeZone() {
        return app_prefs.getString(TIME_ZONE, "");
    }

    public void putStartTime(String startTime) {
        Editor edit = app_prefs.edit();
        edit.putString(START_TIME, startTime);
        edit.commit();
    }

    public String getStartTime() {
        return app_prefs.getString(START_TIME, "");
    }

    public void putRequestCreatedTime(String time) {
        Editor edit = app_prefs.edit();
        edit.putString(CURRENT_TRIP_TIME, time);
        edit.commit();
    }

    public String getRequestCreatedTime() {
        return app_prefs.getString(CURRENT_TRIP_TIME, "");
    }
}
