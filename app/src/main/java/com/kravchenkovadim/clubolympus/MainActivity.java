package com.kravchenkovadim.clubolympus;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kravchenkovadim.clubolympus.data.ClubOlympusContract;
import com.kravchenkovadim.clubolympus.data.ClubOlympusContract.*;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewMembers);

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
        if (cursor == null) return;

        String[] from = new String[]{
                MemberEntry._ID,
                MemberEntry.COLUMN_FIRST_NAME,
                MemberEntry.COLUMN_LAST_NAME,
                MemberEntry.COLUMN_GENDER,
                MemberEntry.COLUMN_SPORT
        };
        int[] to = new int[]{
                R.id.tvId,
                R.id.tvFirstName,
                R.id.tvLastName,
                R.id.tvGender,
                R.id.tvSport
        };

        adapter = new SimpleCursorAdapter(
                this,
                R.layout.item_member,
                cursor,
                from,
                to,
                0
        );

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                int genderIndex = cursor.getColumnIndex(MemberEntry.COLUMN_GENDER);
                if (columnIndex == genderIndex) {
                    int genderValue = cursor.getInt(genderIndex);
                    String genderText;
                    if (genderValue == MemberEntry.GENDER_MALE) genderText = "Male";
                    else if (genderValue == MemberEntry.GENDER_FEMALE) genderText = "Female";
                    else genderText = "Unknown";
                    ((android.widget.TextView) view).setText(genderText);
                    return true;
                }
                return false;
            }
        });

        listView.setAdapter(adapter);
    }
}