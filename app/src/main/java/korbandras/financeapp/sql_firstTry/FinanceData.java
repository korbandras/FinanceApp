package korbandras.financeapp.sql_firstTry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FinanceData {
    private SQLiteDatabase db;
    private FinanceDBHelper helper;

    public FinanceData(Context context){
        helper = new FinanceDBHelper(context);
    }

    public void open() throws SQLException{
        db = helper.getWritableDatabase();
    }

    public void close(){
        helper.close();
    }

    public long newFinanceData(String income, String expenses, String dueDate, String targetSum){
        ContentValues values = new ContentValues();

        values.put(Finance.FinanceEntry.COLUMN_INCOME, income);
        values.put(Finance.FinanceEntry.COLUMN_EXPENSES, expenses);
        values.put(Finance.FinanceEntry.COLUMN_DUEDATE, dueDate);
        values.put(Finance.FinanceEntry.COLUMN_TARGETSUM, targetSum);

        return db.insert(Finance.FinanceEntry.TABLE_NAME,null,values);
    }

    public Cursor getAllData(){
        String[] project = {
                Finance.FinanceEntry.COLUMN_INCOME,
                Finance.FinanceEntry.COLUMN_EXPENSES,
                Finance.FinanceEntry.COLUMN_DUEDATE,
                Finance.FinanceEntry.COLUMN_TARGETSUM
        };

        return db.query(Finance.FinanceEntry.TABLE_NAME, project, null,null,null,null,null);
    }

    public void DeleteAllData(){
        db.delete(Finance.FinanceEntry.TABLE_NAME, null,null);
    }
}
