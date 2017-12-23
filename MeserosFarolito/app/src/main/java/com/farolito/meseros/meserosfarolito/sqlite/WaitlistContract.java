package com.farolito.meseros.meserosfarolito.sqlite;

import android.provider.BaseColumns;

/**
 * Created by Hector on 16/11/2017.
 */

public class WaitlistContract {

    private WaitlistContract(){}

    public static class WaitlistEntry implements BaseColumns{

        public static final String WAITLIST_TABLE_NAME = "questions_table";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CLIENT_NAME = "answered";
        public static final String COLUMN_CLIENT_COUNT = "user_answers";

    }
}
