package com.tylerlutz.brewyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tylerlutz.brewyou.Models.Beer;
import com.tylerlutz.brewyou.Models.Restaurant;

import org.w3c.dom.Text;

/**
 * Created by tyler on 12/13/15.
 */
public class BeerDetailsActivity extends AppCompatActivity {

    private Intent intent;

    private String name;
    private String brewer;
    private String type;

    private int rating;

    private TextView txtName;
    private TextView txtBrewer;
    private TextView txtType;

    private RatingBar ratingBarRating;

    private Button btnUpdate;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_details);

        intent = getIntent();
        final Bundle extras = intent.getExtras();


        final Beer beer = new Beer();
        final Restaurant restaurant = new Restaurant();
        beer.setBeerId(extras.getString("beerid"));
        restaurant.setRestaurantId(extras.getString("restaurantid"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBackToBeerList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BeerListActivity.class);
                intent.putExtra("restaurantid", restaurant.getRestaurantId());
                startActivity(intent);
            }
        });

        txtName = (TextView)findViewById(R.id.txtBeerDetailName);
        txtBrewer = (TextView)findViewById(R.id.txtBeerDetailBrewer);
        txtType = (TextView)findViewById(R.id.txtBeerDetailType);

        ratingBarRating=(RatingBar)findViewById(R.id.rateBarBeerDetail);

        btnUpdate = (Button)findViewById(R.id.btnEditBeer);
        btnDelete = (Button)findViewById(R.id.btnDeleteBeer);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Beer");
        query.getInBackground(beer.getBeerId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseBeer, ParseException e) {
                if (e == null) {
                    name = parseBeer.getString("name");
                    brewer = parseBeer.getString("brewer");
                    type = parseBeer.getString("type");
                    rating = parseBeer.getInt("rating");

                    txtName.setText(name);
                    txtBrewer.setText(brewer);
                    txtType.setText(type);

                    ratingBarRating.setRating(rating);

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UpdateBeerActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Beer");
                query.getInBackground(beer.getBeerId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parseBeer, ParseException e) {
                        if (e == null) {
                            parseBeer.deleteInBackground();

                            Intent intent = new Intent(getApplicationContext(),BeerListActivity.class);
                            intent.putExtra("restaurantid",restaurant.getRestaurantId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
