package korbandras.financeapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoadData extends Activity {
    private TextView loadedData;
    private ListView listView;
    private Button addNew;
    private DBManager dbManager;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loaddata);

        addNew = findViewById(R.id.addNew);
        loadedData = findViewById(R.id.LoadedData);
        listView = findViewById(R.id.Percentage);
        dbManager = new DBManager(this);
        dbManager.open();

        if(dbManager.isDatabaseEmpty()){
            showError();
        }else{
            populateListView();
        }

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadData.this, NewData.class);
                startActivity(intent);
            }
        });
    }

    private void showError() {
        Toast.makeText(this, "Database is empty, please add data", Toast.LENGTH_SHORT).show();
        addNew.setVisibility(View.VISIBLE); // Show the "Add New" button
    }

    private void populateListView() {
        Cursor cursor = dbManager.fetch();
        ArrayList<String> list = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()){
            do {
                String income = cursor.getString(cursor.getColumnIndex(Finance.FinanceEntry.COLUMN_INCOME));
                String expenses = cursor.getString(cursor.getColumnIndex(Finance.FinanceEntry.COLUMN_EXPENSES));
                String dueDate = cursor.getString(cursor.getColumnIndex(Finance.FinanceEntry.COLUMN_DUEDATE));
                String targetSum = cursor.getString(cursor.getColumnIndex(Finance.FinanceEntry.COLUMN_TARGETSUM));
                String data = "Income: " + income + ", Expenses: " + expenses + ", Due Date: " + dueDate + ", Target Sum: " + targetSum;
                list.add(data);
            } while (cursor.moveToNext());
            cursor.close();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        dbManager.close();
    }

}
