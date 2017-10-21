package edu.orangecoastcollege.cs273.mpaulding.gamersdelight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.List;

public class GameListActivity extends AppCompatActivity {

    private DBHelper db;
    private List<Game> gamesList;
    private GameListAdapter gamesListAdapter;
    private ListView gamesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        this.deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);

        db.addGame(new Game("League of Legends", "Multiplayer online battle arena", 4.5f, "lol.png"));
        db.addGame(new Game("Dark Souls III", "Action role-playing", 4.0f, "ds3.png"));
        db.addGame(new Game("The Division", "Single player experience", 3.5f, "division.png"));
        db.addGame(new Game("Doom FLH", "First person shooter", 2.5f, "doomflh.png"));
        db.addGame(new Game("Battlefield 1", "Single player campaign", 5.0f, "battlefield1.png"));

        // TODO:  Populate all games from the database into the list
        // TODO:  Create a new ListAdapter connected to the correct layout file and list
        // TODO:  Connect the ListView with the ListAdapter
        gamesList = db.getAllGames();
        gamesListAdapter = new GameListAdapter(this, R.layout.game_list_item, gamesList);
        gamesListView = (ListView) findViewById(R.id.gameListView);
        gamesListView.setAdapter(gamesListAdapter);
    }

    public void viewGameDetails(View view) {

        // TODO: Use an Intent to start the GameDetailsActivity with the data it needs to correctly inflate its views.
        Intent detailsIntent = new Intent(this, GameDetailsActivity.class);

        Game selectedGame = (Game) view.getTag();

        detailsIntent.putExtra("Name", selectedGame.getName());
        detailsIntent.putExtra("Description", selectedGame.getDescription());
        detailsIntent.putExtra("Rating", selectedGame.getRating());
        detailsIntent.putExtra("ImageFileName", selectedGame.getImageName());

        startActivity(detailsIntent);
    }

    public void addGame(View view)
    {
        // TODO:  Add a game to the database, list, list adapter
        // TODO:  Make sure the list adapter is updated
        // TODO:  Clear all entries the user made (edit text and rating bar)
        EditText mGameNameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText mGameDescriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        RatingBar mGameRatingBar = (RatingBar) findViewById(R.id.gameRatingBar);

        if (TextUtils.isEmpty(mGameNameEditText.getText())
                || TextUtils.isEmpty(mGameDescriptionEditText.getText()))
            Toast.makeText(this, "All the information about the game should be provided.", Toast.LENGTH_LONG).show();
        else
        {
            String name = mGameNameEditText.getText().toString();
            String description = mGameDescriptionEditText.getText().toString();
            float rating = mGameRatingBar.getRating();

            Game newGame = new Game(name, description, rating);

            db.addGame(newGame);
            gamesList.add(newGame);
            gamesListAdapter.notifyDataSetChanged();

            mGameNameEditText.setText("");
            mGameDescriptionEditText.setText("");
            mGameRatingBar.setRating(0f);
        }
    }

    public void clearAllGames(View view)
    {
        // TODO:  Delete all games from the database and lists
        db.deleteAllGames();
        gamesList.clear();
        gamesListAdapter.notifyDataSetChanged();
    }

}
