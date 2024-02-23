package korbandras.financeapp.sql_firstTry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import korbandras.financeapp.sql_firstTry.Finance;


public class FinanceDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Finance";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + Finance.FinanceEntry.TABLE_NAME + " (" +
                    Finance.FinanceEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    Finance.FinanceEntry.COLUMN_INCOME + " TEXT," +
                    Finance.FinanceEntry.COLUMN_EXPENSES + " TEXT," +
                    Finance.FinanceEntry.COLUMN_DUEDATE + " TEXT," +
                    Finance.FinanceEntry.COLUMN_TARGETSUM + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Finance.FinanceEntry.TABLE_NAME;

    public FinanceDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
