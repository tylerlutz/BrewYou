package com.tylerlutz.brewyou.Models;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by michaelmoser on 11/30/15.
 */
public class Restaurant {
    private String restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantCity;
    private String restaurantState;
    private String restaurantZip;
    private Date restaurantFirstVisit;
    private Date restaurantLastVisit;
    private Double restaurantLatitude;
    private Double restaurantLongitude;

    private static final String GOOGLE_GEOCODER_URL = "https://maps.google.com/maps/api/geocode/json&sensor=false&?address=";

    public Restaurant() {
    }

    public Restaurant(String restaurantName, String restaurantAddress,
                      String restaurantCity, String restaurantState,
                      String restaurantZip) {

        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantCity = restaurantCity;
        this.restaurantState = restaurantState;
        this.restaurantZip = restaurantZip;
        this.restaurantFirstVisit = Calendar.getInstance().getTime();

        try {
            findRestaurantCoordinates();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantCity() {
        return restaurantCity;
    }

    public void setRestaurantCity(String restaurantCity) {
        this.restaurantCity = restaurantCity;
    }

    public String getRestaurantState() {
        return restaurantState;
    }

    public void setRestaurantState(String restaurantState) {
        this.restaurantState = restaurantState;
    }

    public String getRestaurantZip() {
        return restaurantZip;
    }

    public void setRestaurantZip(String restaurantZip) {
        this.restaurantZip = restaurantZip;
    }

    public Date getRestaurantFirstVisit() {
        return restaurantFirstVisit;
    }

    public void setRestaurantFirstVisit() {
        this.restaurantFirstVisit = Calendar.getInstance().getTime();
    }

    public Date getRestaurantLastVisit() {
        if (restaurantLastVisit == null) {
            return restaurantFirstVisit;
        } else {
            return restaurantLastVisit;
        }
    }

    public void setRestaurantLastVisit() {
        this.restaurantLastVisit = Calendar.getInstance().getTime();
    }

    public String findRestaurantId(String name) {
        List<Restaurant> restaurants;
        {
            restaurants = new ArrayList<Restaurant>();

        }

        for (Restaurant restaurant : restaurants) {
            if (restaurant.getRestaurantName().equals(name)) {
                return restaurant.getRestaurantId();
            }
        }
        return null;
    }

    public Double getRestaurantLongitude() {
        return restaurantLongitude;
    }

    public void setRestaurantLongitude(Double restaurantLongitude) {
        this.restaurantLongitude = restaurantLongitude;
    }

    public Double getRestaurantLatitude() {
        return restaurantLatitude;
    }

    public void setRestaurantLatitude(Double restaurantLatitude) {
        this.restaurantLatitude = restaurantLatitude;
    }

    public void findRestaurantCoordinates() throws JSONException {
        String response;
        String tempAdd = this.getRestaurantAddress() + ", " + this.getRestaurantCity() + ", " +
                this.getRestaurantState() + ", " + this.getRestaurantZip();
        String address = tempAdd.replace(" ", "+");
        response = getLatLongByURL(GOOGLE_GEOCODER_URL + address);
        Log.d("response", "" + response);

        JSONObject jsonObject = new JSONObject(response);
        double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lng");
        double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lat");
        setRestaurantLatitude(lat);
        setRestaurantLongitude(lng);
    }



    public String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}