package com.tylerlutz.brewyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tylerlutz.brewyou.Adapters.BeerAdapter;
import com.tylerlutz.brewyou.Models.Beer;
import com.tylerlutz.brewyou.Models.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler on 12/12/15.
 */
public class BeerListActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_list);

        intent = getIntent();
        final Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(intent.getStringExtra("restaurantid"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.createBeer);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateBeerActivity.class);
                intent.putExtra("restaurantid",restaurant.getRestaurantId());
                startActivity(intent);
            }
        });


        ListView beerListView = (ListView)findViewById(R.id.listViewBeers);
        List<Beer> beerList = new ArrayList<>();
        final BeerAdapter beerAdapter = new BeerAdapter(this,
                R.layout.layout_for_each_beer,beerList);
        beerListView.setAdapter(beerAdapter);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Beer");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseBeerList, ParseException e) {
                if(e==null){
                    for(ParseObject beers : parseBeerList){
                        if(beers.getString("restaurantid").equals(restaurant.getRestaurantId())){
                            Beer newBeer = new Beer();
                            newBeer.setBeerId(beers.getObjectId());
                            newBeer.setBeerName(beers.getString("name"));
                            newBeer.setBeerBrewer(beers.getString("brewer"));
                            newBeer.setBeerRating(Integer.parseInt(beers.getString("rating")));
                            newBeer.setBeerType(beers.getString("type"));
                            beerAdapter.add(newBeer);
                        }
                    }
                }
            }
        });

        beerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Beer beer = beerAdapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(),BeerDetailsActivity.class);
                intent.putExtra("beerid",beer.getBeerId());
                startActivity(intent);
            }
        });

    }
}
