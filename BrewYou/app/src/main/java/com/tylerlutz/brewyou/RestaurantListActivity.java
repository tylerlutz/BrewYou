package com.tylerlutz.brewyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.tylerlutz.brewyou.Models.Restaurant;
import com.tylerlutz.brewyou.Adapters.RestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.createRestaurant);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateRestaurantActivity.class);
                startActivity(intent);
            }
        });

        if (ParseUser.getCurrentUser() == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else {
            //Generate Restaurants
            ListView listViewRestaurants = (ListView) findViewById(R.id.listViewRestaurants);
            List<Restaurant> restaurants = new ArrayList<>();
            final RestaurantAdapter arrayAdapter = new RestaurantAdapter(this,
                    R.layout.layout_for_each_restaurant, restaurants);
            listViewRestaurants.setAdapter(arrayAdapter);

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> restaurantList, ParseException e) {
                    if (e == null) {
                        for (ParseObject restaurant : restaurantList) {
                            if (restaurant.getString("user").equals(ParseUser.getCurrentUser().getObjectId())) {
                                Restaurant addRestaurant = new Restaurant();
                                addRestaurant.setRestaurantId(restaurant.getObjectId());
                                addRestaurant.setRestaurantName(restaurant.getString("name"));
                                addRestaurant.setRestaurantAddress(restaurant.getString("address"));
                                addRestaurant.setRestaurantCity(restaurant.getString("city"));
                                addRestaurant.setRestaurantState(restaurant.getString("state"));
                                addRestaurant.setRestaurantZip(restaurant.getString("zip"));
                                arrayAdapter.add(addRestaurant);
                            }
                        }
                    }
                }
            });

            listViewRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Restaurant restaurant = arrayAdapter.getItem(position);
                    Intent intent = new Intent(getApplicationContext(),BeerListActivity.class);
                    intent.putExtra("restaurantid",restaurant.getRestaurantId());
                    startActivity(intent);
                }
            });

            listViewRestaurants.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Restaurant restaurant = arrayAdapter.getItem(position);
                    Intent intent = new Intent(getApplicationContext(), RestaurantDetailsActivity.class);
                    intent.putExtra("restaurantid", restaurant.getRestaurantId());
                    startActivity(intent);
                    return false;
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logoff) {
            ParseUser.logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
