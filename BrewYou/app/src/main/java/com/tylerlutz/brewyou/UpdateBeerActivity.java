package com.tylerlutz.brewyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RatingBar;

import com.tylerlutz.brewyou.Models.Beer;

/**
 * Created by Tyler on 12/13/15.
 */
public class UpdateBeerActivity extends AppCompatActivity {

    Intent intent;

    EditText txtName;
    EditText txtBrewer;
    EditText txtType;

    RatingBar ratingBarRating;

    String name;
    String brewer;
    String type;
    String rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_beer);

        intent = getIntent();
        Beer beer = new Beer();
        beer.setBeerId(intent.getStringExtra("beerid"));


    }
}
