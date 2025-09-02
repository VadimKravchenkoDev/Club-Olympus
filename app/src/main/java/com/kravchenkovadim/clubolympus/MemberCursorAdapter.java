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

    private final LayoutInflater layoutInflater;

    public MemberCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_member, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        int id = cursor.getInt(cursor.getColumnIndexOrThrow(MemberEntry._ID));
        String firstName = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_FIRST_NAME));
        String lastName = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_LAST_NAME));
        int gender = cursor.getInt(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_GENDER));
        String sport = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_SPORT));

        holder.tvId.setText(String.valueOf(id));
        holder.tvFirstName.setText(firstName);
        holder.tvLastName.setText(lastName);
        holder.tvGender.setText(genderToText(gender));
        holder.tvSport.setText(sport);
    }

    private String genderToText(int genderValue) {
        if (genderValue == MemberEntry.GENDER_MALE) return "Male";
        if (genderValue == MemberEntry.GENDER_FEMALE) return "Female";
        return "Unknown";
    }

    private static class ViewHolder {
        final TextView tvId;
        final TextView tvFirstName;
        final TextView tvLastName;
        final TextView tvGender;
        final TextView tvSport;

        ViewHolder(View root) {
            this.tvId = root.findViewById(R.id.tvId);
            this.tvFirstName = root.findViewById(R.id.tvFirstName);
            this.tvLastName = root.findViewById(R.id.tvLastName);
            this.tvGender = root.findViewById(R.id.tvGender);
            this.tvSport = root.findViewById(R.id.tvSport);
        }
    }
}


