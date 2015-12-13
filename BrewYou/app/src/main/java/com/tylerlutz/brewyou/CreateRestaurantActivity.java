package com.tylerlutz.brewyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;


/**
 * Created by Tyler on 12/8/15.
 */
public class CreateRestaurantActivity extends AppCompatActivity {
    private EditText name;
    private EditText address;
    private EditText city;
    private EditText state;
    private EditText zip;

    private Button buttonCreate;

    private boolean validationError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);

        name = (EditText)findViewById(R.id.txtCreateRestaurantName);
        address = (EditText)findViewById(R.id.txtCreateRestaurantAddress);
        city = (EditText)findViewById(R.id.txtCreateRestaurantCity);
        state = (EditText)findViewById(R.id.txtCreateRestaurantState);
        zip = (EditText)findViewById(R.id.txtCreateRestaurantZip);
        buttonCreate = (Button)findViewById(R.id.btnCreateRestaurant);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder validationErrorMessage = new StringBuilder("Please ");

                if (isEmpty(name)) {
                    validationError = true;
                    validationErrorMessage.append("enter the Restaurant's name");
                } else if (isEmpty(address)) {
                    if (validationError) {
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter the Restaurant's address");
                } else if (isEmpty(city)) {
                    if (validationError) {
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter the Restaurant's city");
                } else if (isEmpty(state)) {
                    if (validationError) {
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter the Restaurant's state");
                } else if (isEmpty(zip)) {
                    if (validationError) {
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter the Restaurant's zip code");
                }
                validationErrorMessage.append(".");

                if (validationError) {
                    Toast.makeText(CreateRestaurantActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                ParseObject restaurant= new ParseObject("Restaurant");
                restaurant.put("user", ParseUser.getCurrentUser().getObjectId());
                restaurant.put("name",name.getText().toString());
                restaurant.put("address",address.getText().toString());
                restaurant.put("city",city.getText().toString());
                restaurant.put("state", state.getText().toString());
                restaurant.put("zip", zip.getText().toString());
                restaurant.saveInBackground();

                Intent intent = new Intent(getApplicationContext(),RestaurantListActivity.class);
                startActivity(intent);
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
