package com.example.android.bookfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        //Find the view that shows the Search button
        Button searchButton = (Button) findViewById(R.id.topic_search_button);
        //Set click listener on Search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link BookActivity} when button is clicked
                Intent booksIntent = new Intent(MainActivity.this, BookActivity.class);
                startActivity(booksIntent);
            }
        });
    }
}
