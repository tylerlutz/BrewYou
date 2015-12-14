package com.tylerlutz.brewyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tylerlutz.brewyou.Models.Beer;
import com.tylerlutz.brewyou.Models.Restaurant;

/**
 * Created by Tyler on 12/13/15.
 */
public class UpdateBeerActivity extends AppCompatActivity {

   private Intent intent;

    private EditText txtName;
    private EditText txtBrewer;
    private EditText txtType;

    private RatingBar ratingBarRating;

    private String name;
    private String brewer;
    private String type;

    private int rating;

    private Button btnUpdate;

    private boolean validationError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_beer);

        txtName = (EditText)findViewById(R.id.txtUpdateBeerName);
        txtBrewer = (EditText)findViewById(R.id.txtUpdateBeerBrewer);
        txtType = (EditText)findViewById(R.id.txtUpdateBeerType);

        ratingBarRating = (RatingBar)findViewById(R.id.rateBarUpdateBeerRating);
        ratingBarRating.setStepSize(1);

        btnUpdate = (Button)findViewById(R.id.btnBeerUpdate);

        intent = getIntent();
        final Bundle extras = intent.getExtras();
        final Beer beer = new Beer();
        beer.setBeerId(extras.getString("beerid"));
        final Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(extras.getString("restaurantid"));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Beer");
        query.getInBackground(beer.getBeerId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseBeer, ParseException e) {
                if(e==null){
                    name = parseBeer.getString("name");
                    brewer = parseBeer.getString("brewer");
                    type = parseBeer.getString("type");
                    rating = parseBeer.getInt("rating");

                    txtName.setText(name);
                    txtBrewer.setText(brewer);
                    txtType.setText(type);

                    ratingBarRating.setRating(rating);
                    Log.d("Rating",String.valueOf(rating));
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder validationErrorMessage = new StringBuilder("Please ");

                if(isEmpty(txtName)){
                    validationError = true;
                    validationErrorMessage.append("enter the beer's name");
                }else if(isEmpty(txtBrewer)){
                    if(validationError){
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter the beer's brewer");
                }else if(isEmpty(txtBrewer)){
                    if(validationError){
                        validationErrorMessage.append(", and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter the beer's type");
                }else if(ratingBarRating.getRating() == 0){
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
                    name = txtName.getText().toString();
                    brewer = txtBrewer.getText().toString();
                    type = txtType.getText().toString();
                    rating = Math.round(ratingBarRating.getRating());
                    Log.d("Rating Change",String.valueOf(rating));
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Beer");
                    query.getInBackground(beer.getBeerId(), new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject parseBeer, ParseException e) {
                            if(e==null){
                                parseBeer.put("name",name);
                                parseBeer.put("brewer",brewer);
                                parseBeer.put("type",type);
                                parseBeer.put("rating",rating);
                                parseBeer.saveInBackground();

                                Intent intent = new Intent(getApplicationContext(),BeerDetailsActivity.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(),"An error occured.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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
