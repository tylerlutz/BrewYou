package com.tylerlutz.brewyou.Models;

import android.text.format.Time;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created by michaelmoser on 11/30/15.
 */
public class Restaurant {
    private int restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantCity;
    private String restaurantState;
    private String restaurantZip;
    private Date restaurantFirstVisit;
    private Date restaurantLastVisit;

    public Restaurant (){}

    public Restaurant (String restaurantName, String restaurantAddress,
                       String restaurantCity, String restaurantState,
                       String restaurantZip){

        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantCity = restaurantCity;
        this.restaurantState = restaurantState;
        this.restaurantZip = restaurantZip;
        this.restaurantFirstVisit = Calendar.getInstance().getTime();
    }

    public int getRestaurantId(){
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId){
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName(){
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName){
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress(){
        return restaurantAddress;
    }

    public void setRestaurantAddress (String restaurantAddress){
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantCity(){
        return restaurantCity;
    }

    public void setRestaurantCity(String restaurantCity){
        this.restaurantCity = restaurantCity;
    }

    public String getRestaurantState(){
        return restaurantState;
    }

    public void setRestaurantState(String restaurantState){
        this.restaurantState = restaurantState;
    }

    public String getRestaurantZip(){
        return restaurantZip;
    }

    public void setRestaurantZip(String restaurantZip){
        this.restaurantZip = restaurantZip;
    }

    public Date getRestaurantFirstVisit(){
        return restaurantFirstVisit;
    }

    public void setRestaurantFirstVisit(){
        this.restaurantFirstVisit = Calendar.getInstance().getTime();
    }

    public Date getRestaurantLastVisit(){
        if (restaurantLastVisit == null){
            return restaurantFirstVisit;
        }
        else {
            return restaurantLastVisit;
        }
    }

    public void setRestaurantLastVisit(){
        this.restaurantLastVisit = Calendar.getInstance().getTime();
    }

    public int findRestaurantId(String name){
        List<Restaurant> restaurants;{
            restaurants = new ArrayList<Restaurant>();

        }

        for (Restaurant restaurant:restaurants){
            if (restaurant.getRestaurantName().equals(name)){
                return restaurant.getRestaurantId();
            }
        }
        return 0;
    }
}
