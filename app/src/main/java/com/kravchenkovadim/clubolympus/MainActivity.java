package com.kravchenkovadim.clubolympus;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kravchenkovadim.clubolympus.data.ClubOlympusContract.*;

public class MainActivity extends AppCompatActivity
implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MEMBER_LOADER=555;
    ListView dataListView;
    MemberCursorAdapter memberCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        Uri currentMemberUri = intent.getData();

        if(currentMemberUri == null){
            setTitle("Add a Member");
        } else {
            setTitle("Edit the Member");
        }
        dataListView = findViewById(R.id.listViewMembers);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });
        memberCursorAdapter = new MemberCursorAdapter(this, null);
        dataListView.setAdapter(memberCursorAdapter);

        dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,
                        AddMemberActivity.class);
                Uri currentMemberUri = ContentUris.withAppendedId(
                        MemberEntry.CONTENT_URI, id);
                intent.setData(currentMemberUri);
                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(MEMBER_LOADER, null,this);
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
        CursorLoader cursorLoader = new CursorLoader(this,
                MemberEntry.CONTENT_URI,
                projection,
                null, null, null
        );



        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        memberCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        memberCursorAdapter.swapCursor(null);
    }
}