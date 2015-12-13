package com.tylerlutz.brewyou.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tylerlutz.brewyou.Models.Beer;
import com.tylerlutz.brewyou.R;

import java.util.List;

/**
 * Created by tyler on 12/12/15.
 */
public class BeerAdapter extends ArrayAdapter<Beer> {

    private Context context;
    private List<Beer> beers;
    private int layout2Inflate;



    public BeerAdapter(Context context, int resource, List<Beer> beers){
        super(context, resource, beers);
        this.context = context;
        this.layout2Inflate = resource;
        this.beers = beers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout2Inflate,parent,false);
        }
        final Beer beer = beers.get(position);
        TextView textViewBeerName = (TextView)convertView.findViewById(R.id.textViewBeerName);
        textViewBeerName.setText(beer.getBeerName());
        TextView textViewBeerRating = (TextView)convertView.findViewById(R.id.textViewBeerRating);
        textViewBeerRating.setText("Rating: ");
        return convertView;
    }
}
