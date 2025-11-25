package com.kravchenkovadim.clubolympus;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;


import com.kravchenkovadim.clubolympus.data.ClubOlympusContract.MemberEntry;


public class AddMemberActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EDIT_MEMBER_LOADER = 111;
    Uri currentMemberUri ;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText sportNameEditText;
    private Spinner genderSpinner;
    private int gender = 0;
    private ArrayAdapter spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Intent intent = getIntent();

        currentMemberUri = intent.getData();
        if(currentMemberUri == null){
            setTitle("Add a Member");
        } else {
            setTitle("Edit the Member");
        }

        firstNameEditText = findViewById(R.id.firstNameEdit);
        lastNameEditText = findViewById(R.id.lastNameEdit);
        sportNameEditText = findViewById(R.id.sportEdit);
        genderSpinner = findViewById(R.id.spinnerID);

        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(spinnerAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selectedGender)) {
                    if (selectedGender.equals("Male")) {
                        gender = MemberEntry.GENDER_MALE;
                    } else if (selectedGender.equals("Female")) {
                        gender = MemberEntry.GENDER_FEMALE;
                    } else {
                        gender = MemberEntry.GENDER_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getSupportLoaderManager().initLoader(EDIT_MEMBER_LOADER, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_member_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_member) {
            insertMember();
            return true;
        } else if (id == R.id.delete_member) {
            return true;
        } else if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertMember() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String sport = sportNameEditText.getText().toString().trim();

        ContentValues contentValues = new ContentValues();

        contentValues.put(MemberEntry.COLUMN_FIRST_NAME, firstName);
        contentValues.put(MemberEntry.COLUMN_LAST_NAME, lastName);
        contentValues.put(MemberEntry.COLUMN_SPORT, sport);
        contentValues.put(MemberEntry.COLUMN_GENDER, gender);

        ContentResolver contentResolver = getContentResolver();

        Uri uri = contentResolver.insert(MemberEntry.CONTENT_URI, contentValues);

        if (uri == null) {
            Toast.makeText(this, "Insertion of data in the table failed ", Toast.LENGTH_LONG).show();
        } else Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {
                MemberEntry._ID,
                MemberEntry.COLUMN_FIRST_NAME,
                MemberEntry.COLUMN_LAST_NAME,
                MemberEntry.COLUMN_GENDER,
                MemberEntry.COLUMN_SPORT
        };
        return new CursorLoader(this,
                currentMemberUri,
                projection,
                null, null, null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(data.moveToFirst()){
            int first
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}