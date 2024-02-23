package korbandras.financeapp.sql_firstTry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class Finance {
    private Finance(){}
    public static class FinanceEntry{
        public static final String TABLE_NAME = "Finance";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_INCOME = "Income";
        public static final String COLUMN_EXPENSES = "Expenses";
        public static final String COLUMN_DUEDATE = "DueDate";
        public static final String COLUMN_TARGETSUM = "Target_Sum";
    }
}

