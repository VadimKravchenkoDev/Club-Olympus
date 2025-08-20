package com.kravchenkovadim.clubolympus.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.kravchenkovadim.clubolympus.data.ClubOlympusContract.MemberEntry;


public class OlympusDbHelper extends SQLiteOpenHelper {
    public OlympusDbHelper( Context context) {
        super(context,  ClubOlympusContract.DATABASE_NAME, null, ClubOlympusContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEBERS_TABLE = "CREATE TABLE " + ClubOlympusContract.MemberEntry.TABLE_NAME + "("
                + MemberEntry._ID + " INTEGER PRIMARY KEY,"
                + MemberEntry.COLUMN_FIRST_NAME + " TEXT,"
                + MemberEntry.COLUMN_LAST_NAME + " TEXT,"
                + MemberEntry.COLUMN_GENDER + " INTEGER NOT NULL,"
                + MemberEntry.COLUMN_SPORT + " TEXT" + ")";
        db.execSQL(CREATE_MEBERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + ClubOlympusContract.DATABASE_NAME);
        onCreate(db);
    }
}
