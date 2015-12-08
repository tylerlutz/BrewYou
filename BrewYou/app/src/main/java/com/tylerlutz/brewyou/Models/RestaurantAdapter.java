package com.tylerlutz.brewyou.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tylerlutz.brewyou.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Tyler on 12/7/15.
 */
public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private String address;
    private Context context;
    private List<Restaurant> restaurants;
    private int layout2Inflate;

    public RestaurantAdapter(Context context, int resource, List<Restaurant> restaurants) {
        super(context, resource, restaurants);
        this.context = context;
        this.layout2Inflate = resource;
        this.restaurants = restaurants;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout2Inflate,parent,false);
        }
        final Restaurant restaurant = restaurants.get(position);
        TextView textViewRestaurantName = (TextView) convertView.findViewById(R.id.textViewRestaurantName);
        textViewRestaurantName.setText(restaurant.getRestaurantName());
        TextView textViewRestaurantAddress = (TextView) convertView.findViewById(R.id.textViewRestaurantAddress);
        textViewRestaurantAddress.setText(restaurant.getRestaurantAddress()+ " " + restaurant.getRestaurantCity() + ", " + restaurant.getRestaurantState());
        return convertView;
    }
}
