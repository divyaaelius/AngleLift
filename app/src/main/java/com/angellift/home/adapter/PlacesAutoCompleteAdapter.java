package com.angellift.home.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.angellift.R;
import com.angellift.home.model.googleplacemodel.PlacesResults;
import com.angellift.home.model.googleplacemodel.Predictions;
import com.angellift.utils.Const;
import com.angellift.webservice.ApiInterface;
import com.angellift.webservice.ApiRetrofit;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Elluminati elluminati.in
 */
public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements
        Filterable {
    public static final String LOG_TAG = "PlacesAutoCompleteAdap";
    private ArrayList<String> resultList = new ArrayList<String>();
    private Context context;
    List<Predictions> predArr;

    public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        if (resultList.size() > index) {
            return resultList.get(index);
        }
        return "";
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.d(LOG_TAG, "*** performFiltering " + constraint.toString());

                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    resultList = autocomplete(constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                if (results != null && results.count > 0) {
                    Log.d(LOG_TAG, "*** count " + results.count);

                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    private ArrayList<String> autocomplete(String input) {


        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(Const.PLACES_API_BASE
                    + Const.TYPE_AUTOCOMPLETE + Const.OUT_JSON);
//			sb.append("?sensor=false&key=" + Const.PLACES_AUTOCOMPLETE_API_KEY);
            sb.append("?sensor=false&key=" + context.getString(R.string.map_api_key_place));
            //Log.d("BROWSER KEY","****************************AIzaSyCfO6PWk2TmS59qgkwkKOse7YiJUQ1LK40");
            // sb.append("&location=" + BeanLocation.getLocation().getLatitude()
            // + "," + BeanLocation.getLocation().getLongitude());
            sb.append("&radius=500");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            Log.d("Autocomplete api", "*** " + sb.toString());

            // AppLog.Log("PlaceAdapter", "Place Url : " + sb.toString());
            URL url = new URL(sb.toString());
            //conn = (HttpURLConnection) url.openConnection();

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
          //  inStream = urlConnection.getInputStream();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            // System.out.println(jsonResults.toString());
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList.clear();
            for (int i = 0; i < predsJsonArray.length(); i++) {
                resultList.add(predsJsonArray.getJSONObject(i).getString(
                        "description"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "**Cannot process JSON results", e);
        }

        Log.d("Autocomplete resultList", "*** " + resultList);

        return resultList;
    }


    private ArrayList<String> autocompleteRetro(String input) {
       /* if (resultList != null) {
            resultList.clear();

        }*/
        ApiInterface apiService = ApiRetrofit.getRetrofitInstanceGoogle().create(ApiInterface.class);
        int radius = 500;
        String types = null;
        predArr = new ArrayList<>();

        try {
            types = URLEncoder.encode(input, "utf8");
            //   https://maps.googleapis.com/maps/api/place/autocomplete/json?sensor=false&key=AIzaSyCfO6PWk2TmS59qgkwkKOse7YiJUQ1LK40&radius=500&input=rajkot
            Log.d(LOG_TAG, "*** apikey " + context.getString(R.string.map_api_key_path));

            //   call = apiService.getPlacelist(types, input, "", radius, context.getString(R.string.map_api_key_place));
            // Log.d(LOG_TAG, "*** apiservice " + apiService.toString());

            apiService.getPlacelist("", input, "", radius, context.getString(R.string.map_api_key_path)).enqueue(new Callback<PlacesResults>() {
                @Override
                public void onResponse(Call<PlacesResults> call, Response<PlacesResults> response) {
                    //PlaceResults places = response.body();
                    Log.d(LOG_TAG, "*** place body " + response.body().getStatus());
                    PlacesResults places = response.body();

                    if (resultList.size()>0)
                        resultList.clear();
                    for (int i = 0; i < places.getPredictions().size(); i++) {
                        resultList.add(places.getPredictions().get(i).getDescription());
                    }
                    Log.d(LOG_TAG, "*** resultList retro " + resultList.toString());

                }

                @Override
                public void onFailure(Call<PlacesResults> call, Throwable t) {
                    Log.d(LOG_TAG, "*** error");

                    t.printStackTrace();
                }
            });


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "*** resultList retro " + resultList.size());

        return resultList;
    }
}
