package korbandras.financeapp.pages;

import static korbandras.financeapp.xml.StoreDataXML.readFromXML;
import static korbandras.financeapp.xml.StoreDataXML.saveToXML;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import korbandras.financeapp.xml.Datas;
import korbandras.financeapp.R;

public class NewData extends Activity {
    private static final String FileName = "Data.xml";
    private EditText editTextIncome;
    private EditText editTextExpenses;
    private EditText editTextDueDate;
    private EditText editTextTargetSum;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newdata);

        editTextIncome = findViewById(R.id.editTextIncome);
        editTextExpenses = findViewById(R.id.editTextExpenses);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        editTextTargetSum = findViewById(R.id.editTextTargetSum);
        calculateButton = (android.widget.Button) findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    private void saveData() {
        ArrayList<Datas> dataList = new ArrayList<>();

        String income = editTextIncome.getText().toString();
        String expenses = editTextExpenses.getText().toString();
        String dueDate = editTextDueDate.getText().toString();
        String sum = editTextTargetSum.getText().toString();

        if(income.isEmpty() || expenses.isEmpty() || dueDate.isEmpty() || sum.isEmpty()){
            Toast.makeText(NewData.this, "Please fill all fields",Toast.LENGTH_SHORT).show();
            return;
        }

        Datas newData = new Datas(income, expenses, dueDate, sum);
        dataList.add(newData);

        try{
            FileOutputStream fos = openFileOutput(FileName, MODE_PRIVATE);
            saveToXML(dataList,fos);
            fos.close();

            Intent intent = new Intent(NewData.this, Loading.class);
            intent.putExtra("Income",income);
            intent.putExtra("Expenses",expenses);
            intent.putExtra("DueDate",dueDate);
            intent.putExtra("Sum",sum);
            startActivity(intent);
        }catch(IOException e){
            e.printStackTrace();
            Toast.makeText(NewData.this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }
}

