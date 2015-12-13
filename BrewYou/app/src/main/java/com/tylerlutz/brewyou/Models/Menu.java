package com.tylerlutz.brewyou.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelmoser on 12/2/15.
 */
public class Menu {
    private int menuId;
    private String restaurantId;
    private int beerId;

    public Menu(){}

    public Menu (String restaurant, String beer) {
        if (findRestaurantId(restaurant) != null) {
            this.restaurantId = findRestaurantId(restaurant);
        }
        if (findBeerId(beer) != 0){
            this.beerId = findBeerId(beer);
        }
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getBeerId() {
        return beerId;
    }

    public void setBeerId(int beerId) {
        this.beerId = beerId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId =  restaurantId;
    }

    public String findRestaurantId(String name){
        List<Restaurant> restaurants;{
            restaurants = new ArrayList<Restaurant>();

        }

        for (Restaurant restaurant:restaurants){
            if (restaurant.getRestaurantName().equals(name)){
                return restaurant.getRestaurantId();
            }
        }
        return null;
    }

    public int findBeerId(String name){
        List<Beer> beers;{
            beers = new ArrayList<Beer>();
        }

        for (Beer beer:beers){
            if (beer.getBeerName().equals(name)){
                return beer.getBeerId();
            }
        }
        return 0;
    }
}
