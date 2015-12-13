package com.tylerlutz.brewyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tylerlutz.brewyou.Models.Restaurant;

import org.w3c.dom.Text;

/**
 * Created by Tyler on 12/9/15.
 */
public class RestaurantDetails extends AppCompatActivity {

    private Intent intent;

    private String name;
    private String address;
    private String city;
    private String state;
    private String fullAddress;

    private TextView txtName;
    private TextView txtFullAddress;

    private Button btnUpdate;
    private Button btnDelete;

    private Restaurant restaurant = new Restaurant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        intent = getIntent();

        txtName = (TextView)findViewById(R.id.txtRestaurantDetailName);
        txtFullAddress = (TextView)findViewById(R.id.txtRestaurantDetailAddress);
        btnUpdate = (Button)findViewById(R.id.btnEditRestaurant);
        btnDelete = (Button)findViewById(R.id.btnDeleteRestaurant);

        restaurant.setRestaurantId(intent.getStringExtra("objectid"));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
        query.getInBackground(restaurant.getRestaurantId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseRestaurant, ParseException e) {
                if (e == null) {
                    name = parseRestaurant.getString("name");
                    address = parseRestaurant.getString("address");
                    city = parseRestaurant.getString("city");
                    state = parseRestaurant.getString("state");

                    txtName.setText(name);
                    txtFullAddress.setText(address + " " + city + ", " + state);
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateRestaurantActivity.class);
                intent.putExtra("objectid", restaurant.getRestaurantId());
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
                query.getInBackground(restaurant.getRestaurantId(), new GetCallback<ParseObject>() {
                    public void done(ParseObject parseObject, ParseException e) {
                        if (e == null) {

                            parseObject.deleteInBackground();

                            Intent listActivity = new Intent(getApplicationContext(), RestaurantListActivity.class);
                            startActivity(listActivity);

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
