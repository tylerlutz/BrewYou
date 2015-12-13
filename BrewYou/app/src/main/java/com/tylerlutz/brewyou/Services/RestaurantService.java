package com.tylerlutz.brewyou.Services;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
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
        restaurants = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> restaurantList, ParseException e) {
                if(e==null){
                    for(ParseObject restaurant: restaurantList){
//                       if(restaurant.getString("user").equals(ParseUser.getCurrentUser().getObjectId())){
                            Restaurant addRestaurant = new Restaurant();
                            addRestaurant.setRestaurantName(restaurant.getString("name"));
                            addRestaurant.setRestaurantAddress(restaurant.getString("address"));
                            addRestaurant.setRestaurantCity(restaurant.getString("city"));
                            addRestaurant.setRestaurantState(restaurant.getString("state"));
                            addRestaurant.setRestaurantZip(restaurant.getString("zip"));
                            restaurants.add(addRestaurant);
//                       }
                    }
                }
            }
        });

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
