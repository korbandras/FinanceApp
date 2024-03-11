package korbandras.financeapp.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import korbandras.financeapp.R;
import korbandras.financeapp.xml.Datas;
import korbandras.financeapp.xml.StoreAndLoadXML;

public class LoadData extends Activity {
    private ListView listView;
    private Button addNew;
    private Button modifyID;
    private Button deleteID;
    private Button goHome;
    private EditText idIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loaddata);

        addNew = findViewById(R.id.addNew);
        listView = findViewById(R.id.Percentage);
        modifyID = findViewById(R.id.modifyByID);
        deleteID = findViewById(R.id.deleteByID);
        goHome = findViewById(R.id.Home);
        idIn = findViewById(R.id.editID);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start NewData activity should probably be outside loadData(),
                // unless loadData() conditionally determines if NewData should be started.
                loadData();
                Intent intent = new Intent(LoadData.this, NewData.class);
                startActivity(intent);
            }
        });

        modifyID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadData.this, ModifyData.class);
                String inID = modifyID.getText().toString();
                intent.putExtra("id",inID);
                startActivity(intent);
            }
        });

        loadData(); // Call loadData() to load and display data when the activity starts

        deleteID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadData.this, FinanceApp.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        List<Datas> datasList = StoreAndLoadXML.readFromXML(getApplicationContext());
        if (!datasList.isEmpty()) {
            List<String> displayList = new ArrayList<>();
            for (Datas data : datasList) {
                String displayText = "ID: "+ data.getId() + ", Income: " + data.getIncome() + ", Expenses: " + data.getExpenses() +
                        ", Due Date: " + data.getDueDate() + ", Target Sum: " + data.getTargetSum();
                displayList.add(displayText);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, displayList);
            listView.setAdapter(adapter);
        } else {
            showError();
        }
    }

    private void showError() {
        Toast.makeText(this, "Database is empty, please add data", Toast.LENGTH_SHORT).show();
        addNew.setVisibility(View.VISIBLE); // Show the "Add New" button if not already visible
    }
}
