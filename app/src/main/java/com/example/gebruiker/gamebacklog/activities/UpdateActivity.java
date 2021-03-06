package com.example.gebruiker.gamebacklog.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.gebruiker.gamebacklog.R;
import com.example.gebruiker.gamebacklog.models.Game;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {

    private Spinner sItems;
    private EditText titleGame;
    private EditText platformGame;
    private EditText notesGame;
    private String selectedItemText;
    private String dateNow = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
    private Game updatedGame;
    private FloatingActionButton updateFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sItems = findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems.setAdapter(adapter);

        titleGame = findViewById(R.id.inputTitle);
        platformGame = findViewById(R.id.inputPlatform);
        notesGame = findViewById(R.id.inputNotes);
        sItems = findViewById(R.id.spinnerStatus);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemText = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = getIntent();
        updatedGame = intent.getParcelableExtra(MainActivity.EXTRA_GAME);
        titleGame.setText(updatedGame.getTitle());
        platformGame.setText(updatedGame.getPlatform());
        notesGame.setText(updatedGame.getNotes());
        ArrayAdapter<String> array_spinner=(ArrayAdapter<String>)sItems.getAdapter();
        sItems.setSelection(array_spinner.getPosition(updatedGame.getStatus()));

        final long gameId = updatedGame.getId();

        updateFab = findViewById(R.id.updateGameFab);
        updateFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(titleGame.getText().toString().isEmpty() || platformGame.getText().toString().isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content), "Don't leave the fields empty!", Snackbar.LENGTH_LONG).show();

                } else {
                    Intent intent = new Intent();

                    String titleNewGame = titleGame.getText().toString();
                    String platformNewGame = platformGame.getText().toString();
                    String notesNewGame = notesGame.getText().toString();

                    Game game = new Game(titleNewGame, platformNewGame, notesNewGame, selectedItemText, dateNow);
                    game.setId(gameId);
                    intent.putExtra(MainActivity.EXTRA_GAME, game);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });

        setTitle("Update: " + updatedGame.getTitle());
    }
}

