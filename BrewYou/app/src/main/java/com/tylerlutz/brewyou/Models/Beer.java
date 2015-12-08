package com.tylerlutz.brewyou.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelmoser on 11/30/15.
 */
public class Beer {
    private int beerId;
    private String beerBrewer;
    private String beerName;
    private String beerType;
    private double beerAbv;
    private int beerRating;

    public Beer (){}

    public Beer (int beerId, String beerBrewer, String beerName,
                 String beerType, double beerAbv, int beerRating){
        this.beerBrewer = beerBrewer;
        this.beerName = beerName;
        this.beerType = beerType;
        this.beerAbv = beerAbv;
        this.beerRating = beerRating;
    }

    public int getBeerId (){
        return beerId;
    }

    public void setBeerId(int beerId){
        this.beerId = beerId;
    }

    public String getBeerBrewer(){
        return beerBrewer;
    }

    public void setBeerBrewer(String beerBrewer){
        this.beerBrewer = beerBrewer;
    }

    public String getBeerName (){
        return beerName;
    }

    public void setBeerName(String beerName){
        this.beerName = beerName;
    }

    public String getBeerType() {
        return beerType;
    }

    public void setBeerType(String beerType){
        this.beerType = beerType;
    }

    public double getBeerAbv(){
        return beerAbv;
    }

    public void setBeerAbv(double beerAbv){
        this.beerAbv = beerAbv;
    }

    public int getBeerRating(){
        return beerRating;
    }

    public void setBeerRating(int beerRating){
        this.beerRating = beerRating;
    }

    public int findBeerId(String name){
        List<Beer> beerList;{
            beerList = new ArrayList<Beer>();

        }

        for (Beer beer:beerList){
            if (beer.getBeerName().equals(name)){
                return beer.getBeerId();
            }
        }
        return 0;
    }
}
