package com.kravchenkovadim.clubolympus;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.kravchenkovadim.clubolympus.data.ClubOlympusContract.MemberEntry;

public class MemberCursorAdapter extends CursorAdapter {

    public MemberCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_member, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvId = view.findViewById(R.id.tvId);
        TextView tvFirstName = view.findViewById(R.id.tvFirstName);
        TextView tvLastName = view.findViewById(R.id.tvLastName);
        TextView tvGender = view.findViewById(R.id.tvGender);
        TextView tvSport = view.findViewById(R.id.tvSport);

        int id = cursor.getInt(cursor.getColumnIndexOrThrow(MemberEntry._ID));
        String firstName = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_FIRST_NAME));
        String lastName = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_LAST_NAME));
        int gender = cursor.getInt(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_GENDER));
        String sport = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_SPORT));

        tvId.setText(String.valueOf(id));
        tvFirstName.setText(firstName);
        tvLastName.setText(lastName);
        tvGender.setText(genderToText(gender));
        tvSport.setText(sport);
    }

    private String genderToText(int genderValue) {
        if (genderValue == MemberEntry.GENDER_MALE) return "Male";
        if (genderValue == MemberEntry.GENDER_FEMALE) return "Female";
        return "Unknown";
    }
}


