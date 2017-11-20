package com.farolito.meseros.meserosfarolito;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hector on 16/11/2017.
 */

public class WaitlistDBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "questions_db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + WaitlistContract.WaitlistEntry.WAITLIST_TABLE_NAME + "(" +
                    WaitlistContract.WaitlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WaitlistContract.WaitlistEntry.COLUMN_CLIENT_NAME + " TEXT NOT NULL, " +
                    WaitlistContract.WaitlistEntry.COLUMN_CLIENT_COUNT + " INTEGER NOT NULL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WaitlistContract.WaitlistEntry.WAITLIST_TABLE_NAME;

    public WaitlistDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
