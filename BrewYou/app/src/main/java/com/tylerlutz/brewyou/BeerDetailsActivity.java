package com.tylerlutz.brewyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tylerlutz.brewyou.Models.Beer;

import org.w3c.dom.Text;

/**
 * Created by tyler on 12/13/15.
 */
public class BeerDetailsActivity extends AppCompatActivity {

    private Intent intent;

    private String name;
    private String brewer;
    private String type;

    private String rating;

    private TextView txtName;
    private TextView txtBrewer;
    private TextView txtType;
    private TextView txtRating;

    private Button btnUpdate;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_details);

        intent = getIntent();
        final Beer beer = new Beer();
        beer.setBeerId(intent.getStringExtra("beerid"));

        txtName = (TextView)findViewById(R.id.txtBeerDetailName);
        txtBrewer = (TextView)findViewById(R.id.txtBeerDetailBrewer);
        txtType = (TextView)findViewById(R.id.txtBeerDetailType);
        txtRating = (TextView)findViewById(R.id.txtBeerDetailRating);

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
                    rating = parseBeer.getString("rating");

                    txtName.setText(name);
                    txtBrewer.setText(brewer);
                    txtType.setText(type);
                    txtRating.setText("Rating: " + rating);
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UpdateBeerActivity.class);
                intent.putExtra("beerid",beer.getBeerId());
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

                            Intent intent = new Intent(getApplicationContext(),RestaurantListActivity.class);
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
