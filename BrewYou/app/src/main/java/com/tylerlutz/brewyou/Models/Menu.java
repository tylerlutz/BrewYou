package com.tylerlutz.brewyou.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelmoser on 12/2/15.
 */
public class Menu {
    private int menuId;
    private int restaurantId;
    private int beerId;

    public Menu(){}

    public Menu (String restaurant, String beer) {
        if (findRestaurantId(restaurant) != 0) {
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

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId =  restaurantId;
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
