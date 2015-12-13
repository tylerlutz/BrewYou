package com.tylerlutz.brewyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import com.parse.ParseObject;
import com.tylerlutz.brewyou.Models.Restaurant;

/**
 * Created by tyler on 12/13/15.
 */
public class CreateBeerActivity extends AppCompatActivity {

    private Intent intent;

    private EditText name;
    private EditText brewer;
    private EditText type;

    private NumberPicker rating;

    private Button btnCreate;

    private boolean validationError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_beer);

        intent = getIntent();

        final Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(intent.getStringExtra("restaurantid"));

        rating = (NumberPicker)findViewById(R.id.numPickCreateBeerRating);
        rating.setMaxValue(5);
        rating.setMinValue(0);
        rating.setWrapSelectorWheel(true);

        name = (EditText)findViewById(R.id.txtCreateBeerName);
        brewer = (EditText)findViewById(R.id.txtCreateBeerBrewer);
        type = (EditText)findViewById(R.id.txtCreateBeerType);

        btnCreate = (Button)findViewById(R.id.btnCreateBeer);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder validationErrorMessage = new StringBuilder("Please ");

                if(isEmpty(name)){
                    validationError = true;
                    validationErrorMessage.append("enter the beer's name");
                }else if(isEmpty(brewer)){
                    if(validationError){
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter the beer's brewer");
                }else if(isEmpty(type)){
                    if(validationError){
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter the beer's type");
                }else if(rating.getValue() == 0){
                    if(validationError){
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter a rating from 1-5");
                }
                validationErrorMessage.append(".");

                if (validationError) {
                    Toast.makeText(getApplicationContext(), validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                ParseObject newBeer = new ParseObject("Beer");
                newBeer.put("restaurantid",restaurant.getRestaurantId());
                newBeer.put("name",name.getText().toString());
                newBeer.put("brewer",brewer.getText().toString());
                newBeer.put("type",type.getText().toString());
                newBeer.put("rating", String.valueOf(rating.getValue()));
                newBeer.saveInBackground();

                Intent backToList = new Intent(getApplicationContext(),BeerListActivity.class);
                startActivity(backToList);
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
