package korbandras.financeapp.sql_firstTry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Finance";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS finance (_id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newer){
        db.execSQL("DROP TABLE IF EXISTS finane");
        onCreate(db);
    }
}
