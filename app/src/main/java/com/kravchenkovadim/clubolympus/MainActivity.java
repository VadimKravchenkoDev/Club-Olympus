package com.kravchenkovadim.clubolympus;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kravchenkovadim.clubolympus.data.ClubOlympusContract;
import com.kravchenkovadim.clubolympus.data.ClubOlympusContract.*;

public class MainActivity extends AppCompatActivity {

    TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTextView = findViewById(R.id.dataTextView);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    private void displayData() {
        String[] projection = {
                MemberEntry._ID,
                MemberEntry.COLUMN_FIRST_NAME,
                MemberEntry.COLUMN_LAST_NAME,
                MemberEntry.COLUMN_GENDER,
                MemberEntry.COLUMN_SPORT
        };
        Cursor cursor = getContentResolver().query(
                MemberEntry.CONTENT_URI,
                projection,
                null, null, null
        );
        dataTextView.setText("All members\n\n");
        dataTextView.append(MemberEntry._ID + " " +
                MemberEntry.COLUMN_FIRST_NAME + " " +
                MemberEntry.COLUMN_LAST_NAME + " " +
                MemberEntry.COLUMN_GENDER + " " +
                MemberEntry.COLUMN_SPORT);

        if (cursor == null) {
            return;
        }

        int idIndex = cursor.getColumnIndexOrThrow(MemberEntry._ID);
        int idFirstName = cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_FIRST_NAME);
        int idLastName = cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_LAST_NAME);
        int idGender = cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_GENDER);
        int idSport = cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_SPORT);

        while (cursor.moveToNext()) {
            int currentId = cursor.getInt(idIndex);
            String currentFirstName = cursor.getString(idFirstName);
            String currentLastName = cursor.getString(idLastName);
            int currentGender = cursor.getInt(idGender);
            String currentSport = cursor.getString(idSport);

            dataTextView.append("\n\n" +
                    currentId + " " +
                    currentFirstName + " " +
                    currentLastName + " " +
                    currentGender + " " +
                    currentSport
            );
        }
        cursor.close();
    }
}