package com.kravchenkovadim.clubolympus.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kravchenkovadim.clubolympus.data.ClubOlympusContract.*;

public class OlympusContentProvider extends ContentProvider {

    OlympusDbHelper dbHelper;

    private static final int MEMBERS = 111;
    private static final int MEMBER_ID = 222;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS,
                MEMBERS);

        sURIMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS + "/#",
                MEMBER_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new OlympusDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOder) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        // content://com.android.uraal.clubolympus/members/34
        // projection = { "lastName", "gender" }
        int match = sURIMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                cursor = db.query(MemberEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOder);
                break;

            case MEMBER_ID:
                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(MemberEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOder);
                break;
            //selection = "_id=?"
            // selectionArgs = 34
            default:
                Toast.makeText(getContext(), "Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't query incorrect URI" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        String firstName = contentValues.getAsString(MemberEntry.COLUMN_FIRST_NAME);
        if(firstName==null){
            throw new IllegalArgumentException("You have to input first name " + uri);
        }

        String lastName = contentValues.getAsString(MemberEntry.COLUMN_LAST_NAME);
        if(firstName==null){
            throw new IllegalArgumentException("You have to input first name " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = sURIMatcher.match(uri);

        switch (match) {
            case MEMBERS:
               long id=  db.insert(MemberEntry.TABLE_NAME, null, contentValues);
                if(id == -1){
                    Log.e("insertMethod", "Insertion of data in the table failed for "
                    + uri);
                    return null;
                }
                return  ContentUris.withAppendedId(uri,id);
            default:
                throw new IllegalArgumentException("Insertion of data in the table failed for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = sURIMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                return db.delete(MemberEntry.TABLE_NAME, s, strings);

            case MEMBER_ID:
                s = MemberEntry._ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.delete(MemberEntry.TABLE_NAME, s, strings);

            default:
                Toast.makeText(getContext(), "Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't delete this URI" + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = sURIMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                return db.update(MemberEntry.TABLE_NAME, contentValues, s, strings);

            case MEMBER_ID:
                s = MemberEntry._ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.update(MemberEntry.TABLE_NAME, contentValues, s, strings);

            default:
                Toast.makeText(getContext(), "Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't update this URI" + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sURIMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                return MemberEntry.CONTENT_MULTIPLE_ITEMS;

            case MEMBER_ID:
                return MemberEntry.CONTENT_SINGLE_ITEM;

            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
    }
}
