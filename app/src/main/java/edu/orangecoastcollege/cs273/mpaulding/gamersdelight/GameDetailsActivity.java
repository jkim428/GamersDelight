package edu.orangecoastcollege.cs273.mpaulding.gamersdelight;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;


public class GameDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        ImageView gameDetailsImageView = (ImageView) findViewById(R.id.gameDetailsImageView);
        TextView gameDetailsNameTextView = (TextView) findViewById(R.id.gameDetailsNameTextView);
        TextView gameDetailsDescriptionTextView = (TextView) findViewById(R.id.gameDetailsDescriptionTextView);
        RatingBar gameDetailsRatingBar = (RatingBar) findViewById(R.id.gameDetailsRatingBar);


        Intent detailsIntent = getIntent();
        String name = detailsIntent.getStringExtra("Name");
        String description = detailsIntent.getStringExtra("Description");
        float rating = detailsIntent.getFloatExtra("Rating", 0.0f);
        String imageFileName = detailsIntent.getStringExtra("ImageFileName");

        AssetManager am = this.getAssets();
        try {
            InputStream stream = am.open(imageFileName);
            Drawable event = Drawable.createFromStream(stream, name);
            gameDetailsImageView.setImageDrawable(event);
        }
        catch (IOException ex)
        {
            Log.e("Gamers Delight", "Error loading " + imageFileName, ex);
        }

        gameDetailsNameTextView.setText(name);
        gameDetailsDescriptionTextView.setText(description);
        gameDetailsRatingBar.setRating(rating);
    }
}
