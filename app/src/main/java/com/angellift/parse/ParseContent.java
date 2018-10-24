package com.angellift.parse;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.angellift.home.model.Route;
import com.angellift.home.model.Step;
import com.angellift.utils.Const;
import com.angellift.utils.PolyLineUtils;
import com.angellift.utils.PreferenceHelper;
import com.google.android.gms.maps.model.LatLng;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Elluminati elluminati.in
 */
public class ParseContent {
	private Activity activity;
	private PreferenceHelper preferenceHelper;
	private final String KEY_SUCCESS = "success";
	private final String KEY_ERROR = "error";
	// private final String AGE = "age";
	private final String TYPE = "type";
	private final String MIN_FARE = "min_fare";
	private final String MAX_SIZE = "max_size";
	// private final String NOTES = "notes";
	// private final String IMAGE_URL = "image_url";
	// private final String THINGS_ID = "thing_id";
	private final String KEY_ERROR_CODE = "error_code";
	private final String KEY_ERROR_MESSAGES = "error_messages";
	private final String KEY_WALKER = "walker";
	private final String BILL = "bill";
	private final String KEY_BILL = "bill";

	private final String IS_WALKER_STARTED = "is_walker_started";
	private final String IS_WALKER_ARRIVED = "is_walker_arrived";
	private final String IS_WALK_STARTED = "is_walk_started";
	private final String IS_WALKER_RATED = "is_walker_rated";
	private final String IS_COMPLETED = "is_completed";
	private final String STATUS = "status";
	private final String CONFIRMED_WALKER = "confirmed_walker";
	private final String PAYMENT_TYPE = "payment_type";
	private final String TIME = "time";
	private final String BASE_PRICE = "base_price";
	private final String BASE_DISTANCE = "base_distance";
	private final String DISTANCE_COST = "distance_cost";
	private final String DISTANCE = "distance";
	private final String UNIT = "unit";
	private final String TIME_COST = "time_cost";
	private final String TOTAL = "total";
	private final String CURRENCY = "currency";
	private final String IS_PAID = "is_paid";
	private final String START_TIME = "start_time";

	public static final String DATE = "date";

	private final String TYPES = "types";

	private final String ID = "id";

	private final String ICON = "icon";
	private final String IS_DEFAULT = "is_default";
	private final String PRICE_PER_UNIT_TIME = "price_per_unit_time";
	private final String PRICE_PER_UNIT_DISTANCE = "price_per_unit_distance";

	// private final String STRIPE_TOKEN = "stripe_token";
	private final String LAST_FOUR = "last_four";
	// private final String CREATED_AT = "created_at";
	// private final String UPDATED_AT = "updated_at";
	private final String OWNER_ID = "owner_id";
	private final String CARD_TYPE = "card_type";

	private final String PAYMENTS = "payments";

	private final String REQUESTS = "requests";
	private final String WALKER = "walker";
	private final String CUSTOMER_ID = "customer_id";
	private final String REFERED_USER_BONUS = "refered_user_bonus";
	// private final String REFERRAL_USER_BONUS="refereel_user_bonus";
	private final String REFERRAL_CODE = "referral_code";
	private final String TOTAL_REFERRALS = "total_referrals";
	private final String AMOUNT_EARNED = "total_referrals";
	private final String AMOUNT_SPENT = "total_referrals";
	private final String BALANCE_AMOUNT = "balance_amount";
	private final String WALKERS = "walker_list";
	private final String PROMO_CODE = "promo_code";
	private final String PROMO_BONUS = "promo_bonus";
	private final String REFERRAL_BONUS = "referral_bonus";
	private final String ROUTES = "routes";
	private final String LEGS = "legs";
	private final String TEXT = "text";
	private final String VALUE = "value";
	private final String DURATION = "duration";
	private final String START_ADDRESS = "start_address";
	private final String END_ADDRESS = "end_address";
	private final String START_LOCATION = "start_location";
	private final String END_LOCATION = "end_location";
	private final String LATITUDE = "lat";
	private final String LONGITUDE = "lng";
	private final String STEPS = "steps";
	private final String POLYLINE = "polyline";
	private final String POINTS = "points";
	private final String HTML_INSTRUCTIONS = "html_instructions";
	private final String PHONE_CODE = "phone-code";
	private final String NAME = "name";
	private final String DEST_LATITUDE = "dest_latitude";
	private final String DEST_LONGITUDE = "dest_longitude";
	private final String KMS = "kms";
	private final String CARD_DETAILS = "card_details";
	private final String CARD_ID = "card_id";
	// private final String CHARGE_DETAILS = "charge_details";
	// private final String DISTANCE_PRICE = "distance_price";
	 private final String OWNER = "owner";
	// private final String PAYMENT_TYPE = "payment_type";
	private final String RESULTS = "results";
	private final String ALL_SCHEDULED_REQUEST = "all_scheduled_requests";
	private final String SRC_ADDRESS = "src_address";
	private final String TAG_FUTURE = "future";
	private final String TAG_ONGOING = "ongoing";

	// private final String DRIVER_STATUS = "status";

	public ParseContent(Activity activity) {
		this.activity = activity;
		preferenceHelper = new PreferenceHelper(activity);
	}

	public Route parseRoute(String response, Route routeBean) {

		try {
			Step stepBean;
			JSONObject jObject = new JSONObject(response);
			JSONArray jArray = jObject.getJSONArray(ROUTES);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject innerjObject = jArray.getJSONObject(i);
				if (innerjObject != null) {
					JSONArray innerJarry = innerjObject.getJSONArray(LEGS);
					for (int j = 0; j < innerJarry.length(); j++) {

						JSONObject jObjectLegs = innerJarry.getJSONObject(j);
						routeBean.setDistanceText(jObjectLegs.getJSONObject(
								Const.Params.DISTANCE).getString(TEXT));
						routeBean.setDistanceValue(jObjectLegs.getJSONObject(
								Const.Params.DISTANCE).getInt(VALUE));

						routeBean.setDurationText(jObjectLegs.getJSONObject(
								DURATION).getString(TEXT));
						routeBean.setDurationValue(jObjectLegs.getJSONObject(
								DURATION).getInt(VALUE));

						routeBean.setStartAddress(jObjectLegs
								.getString(START_ADDRESS));
						routeBean.setEndAddress(jObjectLegs
								.getString(END_ADDRESS));

						routeBean.setStartLat(jObjectLegs.getJSONObject(
								START_LOCATION).getDouble(LATITUDE));
						routeBean.setStartLon(jObjectLegs.getJSONObject(
								START_LOCATION).getDouble(LONGITUDE));

						routeBean.setEndLat(jObjectLegs.getJSONObject(
								END_LOCATION).getDouble(LATITUDE));
						routeBean.setEndLon(jObjectLegs.getJSONObject(
								END_LOCATION).getDouble(LONGITUDE));

						JSONArray jstepArray = jObjectLegs.getJSONArray(STEPS);
						if (jstepArray != null) {
							for (int k = 0; k < jstepArray.length(); k++) {
								stepBean = new Step();
								JSONObject jStepObject = jstepArray
										.getJSONObject(k);
								if (jStepObject != null) {

									stepBean.setHtml_instructions(jStepObject
											.getString(HTML_INSTRUCTIONS));
									stepBean.setStrPoint(jStepObject
											.getJSONObject(POLYLINE).getString(
													POINTS));
									stepBean.setStartLat(jStepObject
											.getJSONObject(START_LOCATION)
											.getDouble(LATITUDE));
									stepBean.setStartLon(jStepObject
											.getJSONObject(START_LOCATION)
											.getDouble(LONGITUDE));
									stepBean.setEndLat(jStepObject
											.getJSONObject(END_LOCATION)
											.getDouble(LATITUDE));
									stepBean.setEndLong(jStepObject
											.getJSONObject(END_LOCATION)
											.getDouble(LONGITUDE));

									stepBean.setListPoints(new PolyLineUtils()
											.decodePoly(stepBean.getStrPoint()));
									routeBean.getListStep().add(stepBean);
								}
							}
						}
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return routeBean;
	}


}
