package com.angellift.home;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.angellift.MainActivity;
import com.angellift.R;
import com.angellift.home.adapter.PlacesAutoCompleteAdapter;
import com.angellift.home.model.Route;
import com.angellift.home.model.Step;
import com.angellift.parse.AsyncTaskCompleteListener;
import com.angellift.parse.HttpRequester;
import com.angellift.parse.ParseContent;
import com.angellift.utils.Const;
import com.angellift.utils.ConstMethod;
import com.angellift.utils.LocationHelper;
import com.angellift.utils.PolyLineUtils;
import com.angellift.utils.PreferenceHelper;
import com.angellift.vehicle.VehicalTypeModel;
import com.angellift.vehicle.VehicleTypeAdapter;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements DiscreteScrollView.ScrollStateChangeListener<VehicleTypeAdapter.ViewHolder>, DiscreteScrollView.OnItemChangedListener<VehicleTypeAdapter.ViewHolder>, AsyncTaskCompleteListener, OnMapReadyCallback, LocationHelper.OnLocationReceived, View.OnClickListener, AdapterView.OnItemClickListener {


    // tokencc_bj_ptk7dm_8x8vdt_9f4wr7_6b46r7_tn3
    String TAG = "MapFragment";
    private GoogleMap mMap;
    private View view;
    MainActivity activity;
    private float currentZoom = -1;
    boolean mLocationPermissionGranted;
    private LocationHelper locHelper;
    private Location myLocation;
    private LatLng curretLatLng;
    ImageButton btn_mylocation;
    Button btnCloseDialog, scheduleBtn;
    private Marker markerDestination, markerSource;
    LatLng destlatLang = new LatLng(21.5222,
            70.4579);
    Route route;
    private ArrayList<LatLng> points;
    private PolylineOptions lineOptions;
    private Polyline polyLine;
    private PlacesAutoCompleteAdapter adapterPopUpDestination;
    AutoCompleteTextView etPopupDestination;
    private TextView edtPickupAdd, edtDestinationAdd;
    boolean isSource;
    private Dialog destinationDialog, scheduleDialog;
    private LinearLayout layoutHomeText, layoutWorkText,
            sendReqLayout, linearPickupAddress, vehicleLayout;
    private PreferenceHelper preference;

    TextView tvHomeAddress, tvWorkAddress, pickuptime, cars, payment, showtime, showpayment_type, booking, fareestimate, tvBookRide, tvBooklater;
    SlidingDrawer slidingDrawer;
    private AlertDialog alt_review1;
    int payment_type;
    private ListView nearByList;
    private ProgressBar pbNearby, pbar;
    ImageView imgSrc;
    boolean isPickupaddress = true;
    public ParseContent pContent;
    private String strAddress = null;
    private Address address;
    LinearLayout llBooknow, llCarType;

    public static final String PAYPAL_CLIENT_ID = "AbLgy0hRsq0PmoGK-ws2-jlBIeBVKUUU0xRjbfW1-GAckylz_TDNsh1cMrIiSksc2wpqYC2PisTrKhko";
    String Tocken = "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI0MTRiMGZlMzEyYzdjODgzZTJiMGYwOWZkZmVjMjVhNjdmZWM4NjUyNzFiMTg1MTc4MThlZmQ0N2QxZmZkYTZlfGNyZWF0ZWRfYXQ9MjAxOC0wOS0yMlQwNTo0NDozMi45Mjc5MjA2MzArMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJncmFwaFFMIjp7InVybCI6Imh0dHBzOi8vcGF5bWVudHMuc2FuZGJveC5icmFpbnRyZWUtYXBpLmNvbS9ncmFwaHFsIiwiZGF0ZSI6IjIwMTgtMDUtMDgifSwiY2hhbGxlbmdlcyI6W10sImVudmlyb25tZW50Ijoic2FuZGJveCIsImNsaWVudEFwaVVybCI6Imh0dHBzOi8vYXBpLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb206NDQzL21lcmNoYW50cy8zNDhwazljZ2YzYmd5dzJiL2NsaWVudF9hcGkiLCJhc3NldHNVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImF1dGhVcmwiOiJodHRwczovL2F1dGgudmVubW8uc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbSIsImFuYWx5dGljcyI6eyJ1cmwiOiJodHRwczovL29yaWdpbi1hbmFseXRpY3Mtc2FuZC5zYW5kYm94LmJyYWludHJlZS1hcGkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=";
    DiscreteScrollView scrollView;
    ArrayList<VehicalTypeModel> vehicleArray;

    int carType[] = {R.drawable.car2, R.drawable.car3, R.drawable.car4, R.drawable.car5};

    private boolean isDateSelected = false;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private String selectedDate = "", selectedTime = "",
            startTime = "", appliedPromoCode = "";
    private int selectedHour, selectedMinute;
    AlertDialog alv;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);
        activity = (MainActivity) getActivity();
        initID(view);
        bindMethode();
        BindMap(view);
        return view;
    }

    private void initID(View view) {
        preference = new PreferenceHelper(getActivity());
        pContent = new ParseContent(getActivity());
        btn_mylocation = view.findViewById(R.id.btn_mylocation);
        edtPickupAdd = (TextView) view.findViewById(R.id.mypickloc);
        edtDestinationAdd = (TextView) view
                .findViewById(R.id.driverDropLoc);
        slidingDrawer = view.findViewById(R.id.slidingDrawer);
        booking = view.findViewById(R.id.booking);
        cars = view.findViewById(R.id.cars);
        //  pickuptime = view.findViewById(R.id.pickuptime);
        payment = view.findViewById(R.id.payment);
        pickuptime = view.findViewById(R.id.pickuptime);
        showpayment_type = view.findViewById(R.id.showpayment_type);
        imgSrc = view.findViewById(R.id.imgSrc);
        llBooknow = view.findViewById(R.id.llBooknow);
        llCarType = view.findViewById(R.id.llCarType);
        fareestimate = view.findViewById(R.id.fareestimate);
        tvBookRide = view.findViewById(R.id.tvBookRide);
        tvBooklater = view.findViewById(R.id.tvBooklater);
        fareestimate.setOnClickListener(this);
        tvBookRide.setOnClickListener(this);
        tvBooklater.setOnClickListener(this);
        vehicleArray = new ArrayList<>();
        scrollView = (DiscreteScrollView) view.findViewById(R.id.forecast_city_picker);
        getVehicleType();
        // mapFragment.getMapAsync(this);
        setDrawer();

    }

    private void setDrawer() {
        setListeners();


        // Listeners for sliding drawer
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {

                // Change button text when slider is open
                booking.setText("Book Now");
                slidingDrawer.setBackgroundColor(Color.WHITE);

            }
        });

        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {

                // Change button text when slider is close
                slidingDrawer.setBackgroundColor(Color.TRANSPARENT);
                booking.setText("Book Now");

            }
        });
    }

    private void bindMethode() {
        btn_mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myLocation != null) {
                    LatLng latLang = new LatLng(myLocation.getLatitude(),
                            myLocation.getLongitude());
                    setMarker(latLang, true);
                    animateCameraToMarker(latLang, false);

                    //drawPath(latLang, destlatLang);
                }
            }
        });
    }

    public void setMarker(LatLng latLng) {
        if (mMap != null) {
            markerSource = mMap.addMarker(new MarkerOptions()
                    .position(
                            new LatLng(latLng.latitude,
                                    latLng.longitude))
                    .title(activity.getResources().getString(
                            R.string.text_source_pin_title))
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.pin_client)));
        }

    }

    private void setMarker(LatLng latLng, boolean isSource) {
        Log.d(TAG, "*** setMarker " + latLng + " is source " + isSource);
    /*    if (!MapFragment.this.isVisible())
            return;
        if (getActivity() != null && getActivity().getCurrentFocus() != null) {
            // inputMethodManager.hideSoftInputFromWindow(getActivity()
            // .getCurrentFocus().getWindowToken(), 0);
            //activity.hideKeyboard();
        }*/

        if (latLng != null && mMap != null) {
            if (isSource) {
                if (markerSource == null) {
                    markerSource = mMap.addMarker(new MarkerOptions()
                            .position(
                                    new LatLng(latLng.latitude,
                                            latLng.longitude))
                            .title(activity.getResources().getString(
                                    R.string.text_source_pin_title))
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.pin_client)));
                    markerSource.setDraggable(true);
                } else {
                    markerSource.setPosition(latLng);
                }
                CameraUpdateFactory.newLatLng(latLng);

            } else {
                if (markerDestination == null) {
                    MarkerOptions opt = new MarkerOptions();
                    opt.position(latLng);
                    opt.title(activity.getResources().getString(
                            R.string.text_destination_pin_title));
                    opt.icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.pin_des));
                    markerDestination = mMap.addMarker(opt);

                    markerDestination.setDraggable(true);

                    if (markerSource != null) {
                        Log.d(TAG, "*** markerSource " + markerSource.getPosition().latitude + " markerSource " + markerDestination.getPosition().latitude);
                        Log.d(TAG, "*** markerdestination " + markerDestination.getPosition().latitude + " markerDesination" + markerDestination.getPosition().latitude);

                        LatLngBounds.Builder bld = new LatLngBounds.Builder();

                        bld.include(new LatLng(
                                markerSource.getPosition().latitude,
                                markerSource.getPosition().longitude));
                        bld.include(new LatLng(
                                markerDestination.getPosition().latitude,
                                markerDestination.getPosition().longitude));
                        LatLngBounds latLngBounds = bld.build();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
                                latLngBounds, 30));
                    } else {
                        CameraUpdateFactory.newLatLng(latLng);
                    }

                } else {
                    markerDestination.setPosition(latLng);
                }
            }

            // getAddressFromLocation(markerSource.getPosition(), etSource, activity);
        } else {
            Toast.makeText(getActivity(),
                    getActivity().getString(R.string.dialog_no_location_service),
                    Toast.LENGTH_LONG).show();
        }
    }


    private void setListeners() {

        pickuptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConstMethod.DateAndTimeFunction(getContext(), pickuptime);
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCalling();
            }
        });
        cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "payment", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DialogCalling() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View promptView = layoutInflater.inflate(R.layout.payment_view_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);
        // setup a dialog window
        alertDialogBuilder.setCancelable(true);
        LinearLayout cash = promptView.findViewById(R.id.by_cash);
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_type = 0;
                payment.setText("By Cash");
                alt_review1.dismiss();
            }
        });
        LinearLayout card = promptView.findViewById(R.id.by_card);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_type = 1;
                payment.setText("By Card");
                startBraintree();
                alt_review1.dismiss();
            }
        });

        // create an alert dialog
        alt_review1 = alertDialogBuilder.create();
        alt_review1.show();

    }

    private void startBraintree() {
        DropInRequest dropInRequest = new DropInRequest().clientToken(Tocken);
        startActivityForResult(dropInRequest.getIntent(getContext()), 1234);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234) {
            if (resultCode == Activity.RESULT_OK) {

                DropInResult dropInResult = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);

                String nonce = dropInResult.getPaymentMethodNonce().getNonce();
                Log.e("Nonce", "------->" + nonce);

                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("Cancle", "------->" + Activity.RESULT_CANCELED);
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.e("Error", "------->" + error);
            }

        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        locHelper = new LocationHelper(getContext());
        locHelper.setLocationReceivedLister(this);
        locHelper.onStart();


   /*     adapterSource = new PlacesAutoCompleteAdapter(activity,
                R.layout.autocomplete_list_text);


        adapterDestination = new PlacesAutoCompleteAdapter(activity,
                R.layout.autocomplete_list_text);
        edtPickupAdd.setAdapter(adapterSource);
        edtDestinationAdd.setAdapter(adapterDestination);*/


        edtPickupAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  edtPickupAdd.setText("");
                isPickupaddress = true;
                showDestinationPopup();
                //showDestinationPopup();
            }
        });
        edtDestinationAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPickupaddress = false;
                showDestinationPopup();

            }
        });
     /*   edtPickupAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                final String selectedDestPlace = adapterSource.getItem(arg2);
                Log.d(TAG, "****selected addres" + selectedDestPlace);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final LatLng latlng = getLocationFromAddress(selectedDestPlace);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //  isMapTouched = true;
                                curretLatLng = latlng;
                                isSource = true;
                                setMarker(curretLatLng, isSource);
                                setMarkerOnRoad(curretLatLng, curretLatLng);
                                animateCameraToMarker(curretLatLng, true);
                                //  stopUpdateProvidersLoaction();
                                //  getAllProviders(curretLatLng);
                            }
                        });
                    }
                }).start();
            }
        });*/
        /*edtDestinationAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                final String selectedDestPlace = adapterDestination
                        .getItem(arg2);
                edtDestinationAdd.setText(selectedDestPlace);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final LatLng latlng = getLocationFromAddress(selectedDestPlace);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isSource = false;
                                setMarker(latlng, isSource);
                                setMarkerOnRoad(latlng, latlng);
                            }
                        });
                    }
                }).start();
            }
        });
*/
    }


    private void BindMap(View view) {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapFrg);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "*** onMapReady ");

        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        //  LatLng sydney = new LatLng(22.2296, 70.8685);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                // mLastKnownLocation = null;
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onLocationReceived(LatLng latlong) {
        Log.d(TAG, "*** onLocationReceived ");

    }

    @Override
    public void onLocationReceived(Location location) {
        if (location != null) {
            Log.d(TAG, "*** onLocationReceived location" + location);
            // drawTrip(latlong);
            myLocation = location;
        }
    }

    @Override
    public void onConntected(Bundle bundle) {
        Log.d(TAG, "*** onConntected ");

    }

    @Override
    public void onConntected(Location location) {
        if (location != null) {
            Log.d(TAG, "*** onConntected location" + location);

            myLocation = location;

            // isLocationEnable = true;
            LatLng latLang = new LatLng(location.getLatitude(),
                    location.getLongitude());
            curretLatLng = latLang;
            Log.d(TAG, "*** onConntected curretLatLng" + curretLatLng);
            setMarker(curretLatLng, true);
            //  getAllProviders(latLang);
            animateCameraToMarker(latLang, false);
            //   getAddressFromLocation(latLang, etSource, activity);
        } else {
            //  activity.showLocationOffDialog();
        }
    }

    private void animateCameraToMarker(LatLng latLng, boolean isAnimate) {
        try {
            Log.d(TAG, "***  animateCameraToMarker" + latLng);

            //etSource.setFocusable(false);
            //  etSource.setFocusableInTouchMode(false);
            CameraUpdate cameraUpdate = null;
            cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            if (cameraUpdate != null && mMap != null) {
                if (isAnimate)
                    mMap.animateCamera(cameraUpdate);
                else
                    mMap.moveCamera(cameraUpdate);
            }
            //    etSource.setFocusable(true);
            // etSource.setFocusableInTouchMode(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMarkerOnRoad(LatLng sourceLat, LatLng destinationLat) {
        Log.d(TAG, "*** drawPath " + sourceLat + " desination lat" + destinationLat);
        String msg = null;
        if (sourceLat == null) {
            msg = activity.getString(R.string.text_unable_get_source);
        } else if (destinationLat == null) {
            msg = activity.getString(R.string.text_unable_get_destination);
        }
        if (msg != null) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        String urlPath = getDirectionsUrl(sourceLat, destinationLat);
        map.put(Const.URL,
                urlPath);

        new HttpRequester(activity, map, Const.ServiceCode.DRAW_PATH_ROAD,
                true, this);
        // requestQueue.add(new VolleyHttpRequest(Method.GET, map,
        // Const.ServiceCode.DRAW_PATH_ROAD, this, this));

    }


    public void drawPath(LatLng sourceLat, LatLng destinationLat) {
        Log.d(TAG, "*** drawPath " + sourceLat + " desination lat" + destinationLat);

        String msg = null;
        if (sourceLat == null) {
            msg = activity.getString(R.string.text_unable_get_source);
        } else if (destinationLat == null) {
            msg = activity.getString(R.string.text_unable_get_destination);
        }
        if (msg != null) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            return;
        }
        String url = getDirectionsUrl(sourceLat, destinationLat);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutHomeText:
                if (preference.getHomeAddress() != null) {
                    final LatLng latlng = getLocationFromAddress(preference.getHomeAddress());
                    if (latlng != null) {
                        if (isPickupaddress) {
                            isSource = true;
                            edtPickupAdd.setText(preference.getHomeAddress());

                        } else {
                            isSource = false;
                            edtDestinationAdd.setText(preference.getHomeAddress());

                        }
                        Log.d(TAG, "**** layoutHomeText home address " + preference.getHomeAddress());

                        Log.d(TAG, "**** layoutHomeText click " + latlng);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //  isMapTouched = true;
                                curretLatLng = latlng;
                                setMarker(curretLatLng, isSource);

                                // setMarker(curretLatLng, isSource);
                                setMarkerOnRoad(curretLatLng, curretLatLng);
                                animateCameraToMarker(curretLatLng, true);
                                //  stopUpdateProvidersLoaction();
                                //  getAllProviders(curretLatLng);
                            }
                        });
                    }

                    destinationDialog.dismiss();
                }
                break;
            case R.id.layoutWorkText:
                if (preference.getWorkAddress() != null) {
                    if (isPickupaddress) {
                        isSource = true;
                        edtPickupAdd.setText(preference.getWorkAddress());

                    } else {
                        isSource = false;
                        edtDestinationAdd.setText(preference.getWorkAddress());

                    }
                    final LatLng latlng = getLocationFromAddress(preference.getWorkAddress());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //  isMapTouched = true;
                            curretLatLng = latlng;
                            setMarker(curretLatLng, isSource);
                            animateCameraToMarker(curretLatLng, true);
                            setMarkerOnRoad(curretLatLng, curretLatLng);

                            //  stopUpdateProvidersLoaction();
                            //  getAllProviders(curretLatLng);
                        }
                    });
                    destinationDialog.dismiss();
                }
                break;
            case R.id.btnCloseDialog:
                destinationDialog.dismiss();
                break;
            case R.id.imgClearDest:
                etPopupDestination.setText("");
                break;

            case R.id.fareestimate:
                //    llCarType.setVisibility(View.GONE);
                //  llBooknow.setVisibility(View.VISIBLE);
                break;

            case R.id.tvBookRide:
                OpenDialog();
                //  llCarType.setVisibility(View.VISIBLE);
                // llBooknow.setVisibility(View.GONE);
                break;
            case R.id.tvBooklater:
                //  llCarType.setVisibility(View.VISIBLE);
                // llBooknow.setVisibility(View.GONE);
                mRiderLater();
                break;

        }

    }

    private void mRiderLater() {


        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int date = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);

        isDateSelected = true;
        scheduleDialog = new Dialog(activity);
        scheduleDialog.setContentView(R.layout.picker_dialog);
        scheduleDialog.setTitle(activity.getResources().getString(
                R.string.text_schedule_trip));
        datePicker = (DatePicker) scheduleDialog.findViewById(R.id.date_picker);
        timePicker = (TimePicker) scheduleDialog.findViewById(R.id.time_picker);
        scheduleBtn = (Button) scheduleDialog
                .findViewById(R.id.confirm_schedule);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        datePicker.setMaxDate((System.currentTimeMillis())
                + (2 * 24 * 60 * 60 * 1000));

        selectedDate = String.valueOf(year) + "-" + String.valueOf(month + 1)
                + "-" + String.valueOf(date);

        datePicker.init(year, month, date, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker v, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                selectedDate = String.valueOf(datePicker.getYear()) + "-"
                        + String.valueOf((datePicker.getMonth()) + 1) + "-"
                        + String.valueOf(datePicker.getDayOfMonth());

            }

        });
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        selectedTime = String.valueOf(hour) + ":" + String.valueOf(minute);
        selectedHour = hour;
        selectedMinute = minute;

        scheduleBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (isDateSelected) {
                    isDateSelected = false;
                    datePicker.setVisibility(View.GONE);
                    timePicker.setVisibility(View.VISIBLE);
                    scheduleBtn.setText(activity.getResources().getText(R.string.text_select_time));
                    return;
                }
                int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentApiVersion > android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                    selectedHour = timePicker.getHour();
                    selectedMinute = timePicker.getMinute();
                } else {
                    selectedHour = timePicker.getCurrentHour();
                    selectedMinute = timePicker.getCurrentMinute();
                }
//                selectedHour = timePicker.getCurrentHour();
//                selectedMinute = timePicker.getCurrentMinute();
                selectedTime = selectedHour + ":" + selectedMinute;

                if ((datePicker.getDayOfMonth() == c.get(Calendar.DAY_OF_MONTH))
                        && (selectedHour == c.get(Calendar.HOUR_OF_DAY))
                        && (((selectedMinute - c.get(Calendar.MINUTE)) >= 0) && (((selectedMinute
                        - c
                        .get(Calendar.MINUTE)) <= 30)))) {

                    ConstMethod.showProgressDialog(activity,
                            getString(R.string.text_contacting));

                } else if ((datePicker.getDayOfMonth() == c
                        .get(Calendar.DAY_OF_MONTH))
                        && (selectedHour == c.get(Calendar.HOUR_OF_DAY))
                        && ((selectedMinute - c.get(Calendar.MINUTE)) < 0)) {
                    ConstMethod.showToast(activity,
                            activity.getString(R.string.text_create_trip_for_onward_time1)
                    );
                } else if ((datePicker.getDayOfMonth() == c
                        .get(Calendar.DAY_OF_MONTH))
                        && ((selectedHour - c.get(Calendar.HOUR_OF_DAY)) < 0)) {
                    ConstMethod.showToast(activity,
                            activity.getString(R.string.text_create_trip_for_onward_time2)
                    );
                } else if ((datePicker.getDayOfMonth() == c
                        .get(Calendar.DAY_OF_MONTH))
                        && ((selectedHour - c.get(Calendar.HOUR_OF_DAY)) < 0)) {
                    ConstMethod.showToast(activity,
                            activity.getString(R.string.text_create_trip_for_onward_time3)
                    );
                } else if ((datePicker.getDayOfMonth() == ((c
                        .get(Calendar.DAY_OF_MONTH)) + 2))
                        && ((selectedHour > c.get(Calendar.HOUR_OF_DAY)))) {
                    ConstMethod.showToast(activity,
                            activity.getString(R.string.text_create_request_48_hrs)
                    );
                } else if ((datePicker.getDayOfMonth() == ((c
                        .get(Calendar.DAY_OF_MONTH)) + 2))
                        && ((selectedHour == c.get(Calendar.HOUR_OF_DAY)))
                        && ((selectedMinute - c.get(Calendar.MINUTE)) > 0)) {

                    // if((selectedMinute - c.get(Calendar.MINUTE)) > 0)
                    ConstMethod.showToast(activity,
                            activity.getString(R.string.text_create_request_48_hrs)
                    );
                } else if ((datePicker.getMonth() == ((c.get(Calendar.MONTH)) + 1))
                        && (datePicker.getDayOfMonth() == 2)
                        && (selectedHour > c.get(Calendar.HOUR_OF_DAY))) {
                    ConstMethod.showToast(activity,
                            activity.getString(R.string.text_create_request_48_hrs)
                    );
                } else if ((datePicker.getMonth() == ((c.get(Calendar.MONTH)) + 1))
                        && (datePicker.getDayOfMonth() == 2)
                        && (selectedHour == c.get(Calendar.HOUR_OF_DAY))
                        && ((selectedMinute - c.get(Calendar.MINUTE)) > 0)) {

                    ConstMethod.showToast(activity,
                            activity.getString(R.string.text_create_request_48_hrs)
                    );
                } else {
                    startTime = selectedDate + " " + selectedTime;

                    ConstMethod.showToast(activity,
                            "start time " + startTime);
                   /* activity.pHelper.putStartTime(startTime);
                    Calendar cal = Calendar.getInstance();
                    TimeZone timeZone = cal.getTimeZone();
                    activity.pHelper.putTimeZone(timeZone.getID());
                    scheduleTrip();*/
                }
            }
        });
        scheduleDialog.show();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {

        Log.d(TAG, "**** response ontask " + response);
        switch (serviceCode) {
            case Const.ServiceCode.DRAW_PATH_ROAD:
                if (!TextUtils.isEmpty(response)) {
                    route = new Route();
                    pContent.parseRoute(response, route);
                    final ArrayList<Step> step = route.getListStep();
                    System.out.println("step size****=> " + step.size());
                    points = new ArrayList<LatLng>();
                    lineOptions = new PolylineOptions();

                    for (int i = 0; i < step.size(); i++) {
                        List<LatLng> path = step.get(i).getListPoints();
                        System.out.println("step ***==> " + i + " and "
                                + path.size());
                        points.addAll(path);
                    }
                    if (points != null && points.size() > 0) {
                        setMarker(new LatLng(points.get(0).latitude,
                                points.get(0).longitude), isSource);
                        if (isSource) {
                            getAddressFromLocation(
                                    new LatLng(points.get(0).latitude,
                                            points.get(0).longitude), edtPickupAdd, activity);
                        }
                        // else {
                        // getAddressFromLocation(
                        // new LatLng(points.get(0).latitude,
                        // points.get(0).longitude), etDestination);
                        // }
                        if (markerSource != null && markerDestination != null) {
                            showDirection(markerSource.getPosition(),
                                    markerDestination.getPosition());
                        }
                    }
                }
                break;

            case Const.ServiceCode.DRAW_PATH:
                if (!TextUtils.isEmpty(response)) {
                    route = new Route();
                    pContent.parseRoute(response, route);

                    final ArrayList<Step> step = route.getListStep();
                    System.out.println("step size=====> " + step.size());
                    points = new ArrayList<LatLng>();
                    lineOptions = new PolylineOptions();

                    for (int i = 0; i < step.size(); i++) {

                        List<LatLng> path = step.get(i).getListPoints();
                        System.out.println("step =====> " + i + " and "
                                + path.size());
                        points.addAll(path);

                    }

                    if (polyLine != null)
                        polyLine.remove();
                    lineOptions.addAll(points);
                    lineOptions.width(15);
                    lineOptions.color(activity.getResources().getColor(R.color.colorPrimary)); // #00008B
                    // rgb(0,0,139)

                    if (lineOptions != null && mMap != null) {
                        polyLine = mMap.addPolyline(lineOptions);

                        LatLngBounds.Builder bld = new LatLngBounds.Builder();

                        bld.include(markerSource.getPosition());
                        bld.include(markerDestination.getPosition());
                        LatLngBounds latLngBounds = bld.build();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
                                latLngBounds, 10));
                    }
                }
                break;
        }
    }

    @Override
    public void onCurrentItemChanged(@Nullable VehicleTypeAdapter.ViewHolder viewHolder, int adapterPosition) {
        if (viewHolder != null) {
            Log.d(TAG, "****vehicleArray adapterPosition" + adapterPosition);

            viewHolder.showText();
        }
    }

    @Override
    public void onScrollStart(@NonNull VehicleTypeAdapter.ViewHolder currentItemHolder, int adapterPosition) {
        currentItemHolder.hideText();

    }

    @Override
    public void onScrollEnd(@NonNull VehicleTypeAdapter.ViewHolder currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable VehicleTypeAdapter.ViewHolder currentHolder, @Nullable VehicleTypeAdapter.ViewHolder newCurrent) {

    }

    class DownloadTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            Log.d(TAG, "*** DownloadTask url" + url);

            try {
                data = downloadUrl(url[0]);
                Log.d(TAG, "*** DownloadTask data" + data);

            } catch (Exception e) {
                Log.d(TAG, "Background Task" + e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, String> {

        // Parsing the data in non-ui thread
        @Override
        protected String doInBackground(String... jsonData) {

            JSONObject jObject;
            String routes = "";

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d(TAG, "*** ParserTask" + jObject.toString());
                routes = jObject.toString();
              /*  DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);*/
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "*** ParserTask result" + result);

            if (!result.isEmpty()) {
                Log.d(TAG, "*** ParserTask result" + result.toString());

                route = new Route();
                parseRoute(result, route);

                final ArrayList<Step> step = route.getListStep();
                System.out.println("**** step size=====> " + step.size());
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                for (int i = 0; i < step.size(); i++) {

                    List<LatLng> path = step.get(i).getListPoints();
                    System.out.println("*** step =====> " + i + " and "
                            + path.size());
                    points.addAll(path);

                }

                if (polyLine != null)
                    polyLine.remove();
                lineOptions.addAll(points);
                lineOptions.width(15);
                lineOptions.color(activity.getResources().getColor(R.color.green)); // #00008B
                // rgb(0,0,139)

                if (lineOptions != null && mMap != null) {
                    polyLine = mMap.addPolyline(lineOptions);

                    LatLngBounds.Builder bld = new LatLngBounds.Builder();

                    bld.include(markerSource.getPosition());
                    bld.include(markerDestination.getPosition());
                    LatLngBounds latLngBounds = bld.build();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
                            latLngBounds, 10));
                }
            }


        }
    }

    private void showDirection(LatLng source, LatLng destination) {

        Log.d(TAG, "*** showDirection source" + source + " desination lat" + destination);

        if (source == null || destination == null) {
            return;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL,
                "http://maps.googleapis.com/maps/api/directions/json?origin="
                        + source.latitude + "," + source.longitude
                        + "&destination=" + destination.latitude + ","
                        + destination.longitude + "&sensor=false");
        new HttpRequester(activity, map, Const.ServiceCode.DRAW_PATH, true,
                this);
        // requestQueue.add(new VolleyHttpRequest(Method.GET, map,
        // Const.ServiceCode.DRAW_PATH, this, this));
    }

    public Route parseRoute(String response, Route routeBean) {

        try {
            Step stepBean;
            JSONObject jObject = new JSONObject(response);
            JSONArray jArray = jObject.getJSONArray(Const.Params.ROUTES);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject innerjObject = jArray.getJSONObject(i);
                if (innerjObject != null) {
                    JSONArray innerJarry = innerjObject.getJSONArray(Const.Params.LEGS);
                    for (int j = 0; j < innerJarry.length(); j++) {

                        JSONObject jObjectLegs = innerJarry.getJSONObject(j);
                        routeBean.setDistanceText(jObjectLegs.getJSONObject(
                                Const.Params.DISTANCE).getString(Const.Params.TEXT));
                        routeBean.setDistanceValue(jObjectLegs.getJSONObject(
                                Const.Params.DISTANCE).getInt(Const.Params.VALUE));

                        routeBean.setDurationText(jObjectLegs.getJSONObject(
                                Const.Params.DURATION).getString(Const.Params.TEXT));
                        routeBean.setDurationValue(jObjectLegs.getJSONObject(
                                Const.Params.DURATION).getInt(Const.Params.VALUE));

                        routeBean.setStartAddress(jObjectLegs
                                .getString(Const.Params.START_ADDRESS));
                        routeBean.setEndAddress(jObjectLegs
                                .getString(Const.Params.END_ADDRESS));

                        routeBean.setStartLat(jObjectLegs.getJSONObject(
                                Const.Params.START_LOCATION).getDouble(Const.Params.LAT));
                        routeBean.setStartLon(jObjectLegs.getJSONObject(
                                Const.Params.START_LOCATION).getDouble(Const.Params.LNG));

                        routeBean.setEndLat(jObjectLegs.getJSONObject(
                                Const.Params.END_LOCATION).getDouble(Const.Params.LAT));
                        routeBean.setEndLon(jObjectLegs.getJSONObject(
                                Const.Params.END_LOCATION).getDouble(Const.Params.LNG));

                        JSONArray jstepArray = jObjectLegs.getJSONArray(Const.Params.STEPS);
                        if (jstepArray != null) {
                            for (int k = 0; k < jstepArray.length(); k++) {
                                stepBean = new Step();
                                JSONObject jStepObject = jstepArray
                                        .getJSONObject(k);
                                if (jStepObject != null) {

                                    stepBean.setHtml_instructions(jStepObject
                                            .getString(Const.Params.HTML_INSTRUCTIONS));
                                    stepBean.setStrPoint(jStepObject
                                            .getJSONObject(Const.Params.POLYLINE).getString(
                                                    Const.Params.POINTS));
                                    stepBean.setStartLat(jStepObject
                                            .getJSONObject(Const.Params.START_LOCATION)
                                            .getDouble(Const.Params.LAT));
                                    stepBean.setStartLon(jStepObject
                                            .getJSONObject(Const.Params.START_LOCATION)
                                            .getDouble(Const.Params.LNG));
                                    stepBean.setEndLat(jStepObject
                                            .getJSONObject(Const.Params.END_LOCATION)
                                            .getDouble(Const.Params.LAT));
                                    stepBean.setEndLong(jStepObject
                                            .getJSONObject(Const.Params.END_LOCATION)
                                            .getDouble(Const.Params.LNG));

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

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";
        String mapKey = getString(R.string.map_api_key_path);

        // Building the url to the web service +"&key="+mapKey
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + mapKey;
        Log.d(TAG, "*** getDirectionsUrl " + url);


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private LatLng getLocationFromAddress(final String place) {
        LatLng loc = null;
        Geocoder gCoder = new Geocoder(getActivity());
        try {
            final List<Address> list = gCoder.getFromLocationName(place, 1);
            Log.d(TAG, "*** location from address " + list.toString());
            if (list != null && list.size() > 0) {
                loc = new LatLng(list.get(0).getLatitude(), list.get(0)
                        .getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return loc;
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == android.view.KeyEvent.ACTION_UP
                        && keyCode == android.view.KeyEvent.KEYCODE_BACK) {
                    cancelConfirmation();
                    return true;
                }
                return false;
            }
        });
    }

    private void cancelConfirmation() {
        if (markerSource != null) {
            markerSource.remove();
            markerSource = null;
        }
        if (markerDestination != null) {
            markerDestination.remove();
            markerDestination = null;
        }
        if (polyLine != null)
            polyLine.remove();


        isSource = true;

    }

    private void showDestinationPopup() {
        destinationDialog = new Dialog(getActivity());
        destinationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        destinationDialog.setContentView(R.layout.destination_popup);
        etPopupDestination = (AutoCompleteTextView) destinationDialog
                .findViewById(R.id.etPopupDestination);

        btnCloseDialog = destinationDialog.findViewById(R.id.btnCloseDialog);

        tvHomeAddress = (TextView) destinationDialog
                .findViewById(R.id.tvHomeAddress);
        tvWorkAddress = (TextView) destinationDialog
                .findViewById(R.id.tvWorkAddress);

        layoutHomeText = (LinearLayout) destinationDialog
                .findViewById(R.id.layoutHomeText);

        layoutWorkText = (LinearLayout) destinationDialog
                .findViewById(R.id.layoutWorkText);
        layoutHomeText.setOnClickListener(this);
        layoutWorkText.setOnClickListener(this);
        destinationDialog.findViewById(R.id.imgClearDest).setOnClickListener(this);
        if (preference.getHomeAddress() != null) {
            tvHomeAddress.setText(preference.getHomeAddress());
        }
        if (preference.getWorkAddress() != null) {
            tvWorkAddress.setText(preference.getWorkAddress());
        }


        // nearByList = (ListView) destinationDialog.findViewById(R.id.nearByList);
        // pbNearby = (ProgressBar) destinationDialog.findViewById(R.id.pbNearby);

        adapterPopUpDestination = new PlacesAutoCompleteAdapter(activity,
                R.layout.autocomplete_list_text);
        etPopupDestination.setAdapter(adapterPopUpDestination);

        destinationDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        etPopupDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                final String selectedDestPlace = adapterPopUpDestination.getItem(arg2);

                Log.d(TAG, "****selected addres" + selectedDestPlace);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final LatLng latlng = getLocationFromAddress(selectedDestPlace);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //  isMapTouched = true;
                                curretLatLng = latlng;
                                if (isPickupaddress) {
                                    isSource = true;
                                    edtPickupAdd.setText(selectedDestPlace);
                                } else {
                                    isSource = false;
                                    edtDestinationAdd.setText(selectedDestPlace);
                                }
                                setMarker(curretLatLng, isSource);
                                setMarkerOnRoad(curretLatLng, curretLatLng);
                                animateCameraToMarker(curretLatLng, true);
                                //  stopUpdateProvidersLoaction();
                                //  getAllProviders(curretLatLng);
                                destinationDialog.dismiss();

                            }
                        });
                    }
                }).start();
                // sendQuoteRequest(adapterPopUpDestination.getItem(arg2));
            }
        });
        btnCloseDialog.setOnClickListener(this);
        nearByLocations();
        //   nearByList.setOnItemClickListener(this);
        destinationDialog.show();
    }

    private void closeKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void sendQuoteRequest(String destination) {
       /* pbMinFare.setVisibility(View.VISIBLE);
        tvMinFare.setVisibility(View.GONE);
        // tvLblMinFare.setVisibility(View.GONE);
        tvTotalFare.setVisibility(View.GONE);
        tvGetFareEst.setText(destination);
        getFareQuote(curretLatLng, destination);*/
        destinationDialog.dismiss();
    }

    private void nearByLocations() {
       /* StringBuilder sb = new StringBuilder(Const.PLACES_API_BASE
                + Const.TYPE_NEAR_BY + Const.OUT_JSON);
        Log.d("BROWSER KEY","****************************"+preference.getBrowserKey());

//        sb.append("?sensor=true&key=" + Const.PLACES_AUTOCOMPLETE_API_KEY);
        sb.append("?sensor=true&key=" +preference.getBrowserKey());
        sb.append("&location=" + curretLatLng.latitude + ","
                + curretLatLng.longitude);
        sb.append("&radius=500");
        // AppLog.Log("", "Near location Url : " + sb.toString());
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, sb.toString());
        new HttpRequester(activity, map, Const.ServiceCode.GET_NEAR_BY, true,
                this);*/
        // requestQueue.add(new VolleyHttpRequest(Method.GET, map,
        // Const.ServiceCode.GET_NEAR_BY, this, this));
    }

    private void getAddressFromLocation(final LatLng latlng, final TextView et, final Activity activity) {
        et.setText("Waiting for Address");
        et.setTextColor(Color.GRAY);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Geocoder gCoder = new Geocoder(activity);
                try {
                    final List<Address> list = gCoder.getFromLocation(
                            latlng.latitude, latlng.longitude, 1);
                    if (list != null && list.size() > 0) {
                        address = list.get(0);
                        StringBuilder sb = new StringBuilder();
                        if (address.getAddressLine(0) != null) {
                            if (address.getMaxAddressLineIndex() > 0) {
                                for (int i = 0; i < address
                                        .getMaxAddressLineIndex(); i++) {
                                    sb.append(address.getAddressLine(i))
                                            .append("\n");
                                }
                                sb.append(",");
                                sb.append(address.getCountryName());
                            } else {
                                sb.append(address.getAddressLine(0));
                            }
                        }

                        strAddress = sb.toString();
                        strAddress = strAddress.replace(",null", "");
                        strAddress = strAddress.replace("null", "");
                        strAddress = strAddress.replace("Unnamed", "");
                    }
                    // if (list != null && list.size() > 0) {
                    // address = list.get(0);
                    // StringBuilder sb = new StringBuilder();
                    // if (address.getAddressLine(0) != null) {
                    // for (int i = 0; i < address
                    // .getMaxAddressLineIndex(); i++) {
                    // sb.append(address.getAddressLine(i)).append(
                    // "\n");
                    // }
                    // }
                    // strAddress = sb.toString();
                    // strAddress = strAddress.replace(",null", "");
                    // strAddress = strAddress.replace("null", "");
                    // strAddress = strAddress.replace("Unnamed", "");
                    // }
                    if (getActivity() == null)
                        return;

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(strAddress)) {
                                et.setFocusable(false);
                                et.setFocusableInTouchMode(false);
                                et.setText(strAddress);
                                et.setTextColor(activity.getResources()
                                        .getColor(android.R.color.black));
                                et.setFocusable(true);
                                et.setFocusableInTouchMode(true);
                            } else {
                                et.setText("");
                                et.setTextColor(activity.getResources().getColor(
                                        android.R.color.black));
                            }
                            edtPickupAdd.setEnabled(true);
                        }
                    });
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        }).start();
    }

    void getVehicleType() {
        VehicalTypeModel v1 = new VehicalTypeModel();
        v1.setBaseDistance(carType[0]);
        v1.setName("Car1");
        vehicleArray.add(v1);

        VehicalTypeModel v2 = new VehicalTypeModel();
        v2.setBaseDistance(carType[1]);
        v2.setName("Car2");
        vehicleArray.add(v2);

        VehicalTypeModel v3 = new VehicalTypeModel();
        v3.setBaseDistance(carType[2]);
        v3.setName("Car3");
        vehicleArray.add(v3);

        VehicalTypeModel v4 = new VehicalTypeModel();
        v4.setBaseDistance(carType[3]);
        v4.setName("Car4");
        vehicleArray.add(v4);

        Log.d(TAG, "****vehicleArray size" + vehicleArray.size());


        scrollView.setSlideOnFling(true);
        scrollView.setAdapter(new VehicleTypeAdapter(vehicleArray));
        scrollView.addOnItemChangedListener(this);
        scrollView.addScrollStateChangeListener(this);
        scrollView.scrollToPosition(0);
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
    }

    private void OpenDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View promptView = layoutInflater.inflate(R.layout.conform_dialog, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        Button btn_dialog_conform = promptView.findViewById(R.id.btn_dialog_conform);
        alertDialogBuilder.setView(promptView);
        // setup a dialog window
        alertDialogBuilder.setCancelable(true);

        // sliding button
        btn_dialog_conform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You Accept Request", Toast.LENGTH_SHORT).show();
                // vibrate the device
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                alv.dismiss();

            }
        });


        // create an alert dialog
        alv = alertDialogBuilder.create();
        alv.show();

    }

}