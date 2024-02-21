package korbandras.financeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBManager {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public DBManager(Context c){
        context = c;
    }

    public DBManager open(){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        db.close();
    }

    public void insert(String income, String expenses, String dueDate, String sum) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Finance.FinanceEntry.COLUMN_INCOME, income);
        contentValues.put(Finance.FinanceEntry.COLUMN_EXPENSES, expenses);
        contentValues.put(Finance.FinanceEntry.COLUMN_DUEDATE, dueDate);
        contentValues.put(Finance.FinanceEntry.COLUMN_TARGETSUM, sum);
        db.insert(Finance.FinanceEntry.TABLE_NAME, null, contentValues);
    }

    public Cursor fetch(){
        String[] columns = new String[]{
                Finance.FinanceEntry.COLUMN_INCOME,
                Finance.FinanceEntry.COLUMN_EXPENSES,
                Finance.FinanceEntry.COLUMN_DUEDATE,
                Finance.FinanceEntry.COLUMN_TARGETSUM
        };
        Cursor cursor = db.query(
                Finance.FinanceEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }


    public boolean isDatabaseEmpty() {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Finance", null);
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count == 0;
        }
        return true;
    }

}
