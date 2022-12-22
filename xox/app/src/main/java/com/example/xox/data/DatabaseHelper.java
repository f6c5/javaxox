package com.example.xox.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.xox.model.playHistory;
import com.example.xox.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DB_N, null, Util.DB_V);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_HISTORY_TABLE = "CREATE TABLE " + Util.TBL_N + " (" +
                Util.H_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Util.H_X_NAME + " TEXT, " +
                Util.H_O_NAME + " TEXT, " +
                Util.H_SCORE + " TEXT, " +
                Util.H_MOD+")";
        sqLiteDatabase.execSQL(CREATE_HISTORY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int ni) {

        String DROP_NOTE_TABLE = "DROP TABLE IF EXISTS "+ Util.TBL_N;
        db.execSQL(DROP_NOTE_TABLE, new String[]{Util.TBL_N});

        onCreate(db);

    }

    public long insertHistory (playHistory ph)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Util.H_X_NAME, ph.get_x_name());
        cv.put(Util.H_O_NAME, ph.get_o_name());
        cv.put(Util.H_SCORE, ph.get_score());
        cv.put(Util.H_MOD, ph.get_mod());

        long newRowId = db.insert(Util.TBL_N, null, cv);

        db.close();
        return newRowId;
    }

    public List<playHistory> listHistory(){

        List<playHistory> lh = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TBL_N;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                playHistory ph = new playHistory();
                ph.set_h_id(cursor.getInt(0));
                ph.set_x_name(cursor.getString(1));
                ph.set_o_name(cursor.getString(2));
                ph.set_score(cursor.getString(3));
                ph.set_mod(cursor.getString(4));

                lh.add(ph);

            } while (cursor.moveToNext());

        }
        cursor.close();
        return lh;
    }

    public void deleteHistory()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ Util.TBL_N);
    }
}
