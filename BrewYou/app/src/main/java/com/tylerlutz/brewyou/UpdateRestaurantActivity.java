package com.tylerlutz.brewyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tylerlutz.brewyou.Models.Restaurant;

/**
 * Created by Tyler on 12/12/15.
 */
public class UpdateRestaurantActivity extends AppCompatActivity {

    private Intent intent;

    private EditText txtName;
    private EditText txtAddress;
    private EditText txtCity;
    private EditText txtState;
    private EditText txtZip;

    private Button btnUpdateRestaurant;

    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;

    private boolean validationError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_restaurant);

        txtName = (EditText)findViewById(R.id.txtUpdateRestaurantName);
        txtAddress = (EditText)findViewById(R.id.txtUpdateRestaurantAddress);
        txtCity = (EditText)findViewById(R.id.txtUpdateRestaurantCity);
        txtState = (EditText)findViewById(R.id.txtUpdateRestaurantState);
        txtZip = (EditText)findViewById(R.id.txtUpdateRestaurantZip);

        btnUpdateRestaurant = (Button)findViewById(R.id.btnUpdateRestaurant);


        intent = getIntent();

        final Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(intent.getStringExtra("restaurantid"));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
        query.getInBackground(restaurant.getRestaurantId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseRestaurant, ParseException e) {
                if (e == null) {
                    name = parseRestaurant.getString("name");
                    address = parseRestaurant.getString("address");
                    city = parseRestaurant.getString("city");
                    state = parseRestaurant.getString("state");
                    zip = parseRestaurant.getString("zip");

                    txtName.setText(name);
                    txtAddress.setText(address);
                    txtCity.setText(city);
                    txtState.setText(state);
                    txtZip.setText(zip);

//                    Log.d("Name",name);
//                    Log.d("Address",address);
//                    Log.d("City",city);
//                    Log.d("State",state);
//                    Log.d("Zip",zip);

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnUpdateRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder validationErrorMessage = new StringBuilder("Please ");

                if (isEmpty(txtName)) {
                    validationError = true;
                    validationErrorMessage.append("enter the Restaurant's name");
                } else if (isEmpty(txtAddress)) {
                    if (validationError) {
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter the Restaurant's address");
                } else if (isEmpty(txtCity)) {
                    if (validationError) {
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter the Restaurant's city");
                } else if (isEmpty(txtState)) {
                    if (validationError) {
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter the Restaurant's state");
                }  else if (isEmpty(txtZip)) {
                    if (validationError) {
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter the Restaurant's zip code");
                }
                validationErrorMessage.append(".");

                if (validationError) {
                    Toast.makeText(getApplicationContext(), validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }else{
                    name = txtName.getText().toString();
                    address = txtAddress.getText().toString();
                    city = txtCity.getText().toString();
                    state = txtState.getText().toString();
                    zip = txtZip.getText().toString();

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
                    query.getInBackground(restaurant.getRestaurantId(), new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject parseObject, ParseException e) {
                            if(e==null){
                                parseObject.put("name",name);
                                parseObject.put("address",address);
                                parseObject.put("city",city);
                                parseObject.put("state",state);
                                parseObject.put("zip", zip);
                                parseObject.saveInBackground();

                                Intent intent = new Intent(getApplicationContext(),RestaurantDetailsActivity.class);
                                intent.putExtra("restaurantid",restaurant.getRestaurantId());
                                startActivity(intent);

                            }else {
                                Toast.makeText(getApplicationContext(),"An error occured.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });

    }

    private boolean isEmpty(EditText text){
        if(text.getText().toString().trim().length()> 0){
            return false;
        }else{
            return true;
        }
    }
}
