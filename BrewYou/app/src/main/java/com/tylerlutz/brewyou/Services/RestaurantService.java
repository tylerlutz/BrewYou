package com.tylerlutz.brewyou.Services;

import com.tylerlutz.brewyou.Models.Restaurant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by michaelmoser on 11/30/15.
 */
public class RestaurantService {
    private List<Restaurant> restaurants;
    {
        restaurants = new ArrayList<Restaurant>();
        restaurants.add(new Restaurant("Test Bar","123 Test St","Louisville","KY","40208"));
        restaurants.add(new Restaurant("Test Bar1","1234 Test St","Louisville","KY","40208"));
        restaurants.add(new Restaurant("Test Bar2","1235 Test St","Louisville","KY","40208"));

    }

    public List<Restaurant> findAll(){
        return restaurants;
    }

    public Restaurant findOne(String name){
        for (Restaurant restaurant:restaurants){
            if (restaurant.getRestaurantName().equals(name)){
               return restaurant;
            }
        }
        return new Restaurant();
    }

    private static Date getDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year,month,day,0,0);
        return c.getTime();
    }

}
