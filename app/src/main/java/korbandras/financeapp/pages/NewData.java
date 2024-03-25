package korbandras.financeapp.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import korbandras.financeapp.xml.Datas;
import korbandras.financeapp.R;
import korbandras.financeapp.xml.EnteredData;
import korbandras.financeapp.xml.StoreAndLoadXML;

public class NewData extends Activity {
    private EditText editTextIncome;
    private EditText editTextExpenses;
    private EditText editTextDueDate;
    private EditText editTextTargetSum;
    private Button calculateButton;
    private Button homeAddNew;
    private Button addToDB;
    private Button loadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newdata);

        editTextIncome = findViewById(R.id.editTextIncome);
        editTextExpenses = findViewById(R.id.editTextExpenses);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        editTextTargetSum = findViewById(R.id.editTextTargetSum);
        calculateButton = findViewById(R.id.calculateButton);
        homeAddNew = findViewById(R.id.homeAddNew);
        addToDB = findViewById(R.id.addToDB);
        loadData = findViewById(R.id.loadData_addNew);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnteredData enteredData = extractData();
                saveNew(enteredData);

                Intent intent = new Intent(NewData.this, Loading.class);
                intent.putExtra("Income",enteredData.income);
                intent.putExtra("Expenses",enteredData.expenses);
                intent.putExtra("DueDate",enteredData.dueDate);
                intent.putExtra("Sum",enteredData.targetSum);
                startActivity(intent);
            }
        });

        homeAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHomeif();
            }
        });

        addToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnteredData enteredData = extractData();
                saveNew(enteredData);
                Toast.makeText(NewData.this, "Data saved!", Toast.LENGTH_SHORT).show();
                refreshActivity();
            }
        });

        loadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewData.this, LoadData.class);
                startActivity(intent);
            }
        });
    }

    private void saveData(String income, String expenses, String dueDate, String sum) {
        if (income.isEmpty() || expenses.isEmpty() || dueDate.isEmpty() || sum.isEmpty()) {
            Toast.makeText(NewData.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Datas data = new Datas(income, expenses, dueDate, sum);
        List<Datas> dataList = new ArrayList<>();
        dataList.add(data);

        StoreAndLoadXML.saveXML(getApplicationContext(),dataList);
    }


    private void saveNew(EnteredData enteredData){
        double netpos = Integer.parseInt(enteredData.income) - Integer.parseInt(enteredData.expenses);

        if((double) Integer.parseInt(enteredData.targetSum) / Integer.parseInt(enteredData.dueDate) > netpos || Integer.parseInt(enteredData.income) < Integer.parseInt(enteredData.expenses) || Integer.parseInt(enteredData.dueDate) <= 0 || Integer.parseInt(enteredData.targetSum) <= 0){
            Toast.makeText(NewData.this, "Not possible, won't be saved",Toast.LENGTH_SHORT).show();
        }else{
            saveData(enteredData.income, enteredData.expenses, enteredData.dueDate, enteredData.targetSum);
        }
    }

    private void goHomeif(){
        String income = editTextIncome.getText().toString();
        String expenses = editTextExpenses.getText().toString();
        String dueDate = editTextDueDate.getText().toString();
        String sum = editTextTargetSum.getText().toString();

        Intent intent = new Intent(NewData.this, FinanceApp.class);
        if(!income.isEmpty() || !expenses.isEmpty() || !dueDate.isEmpty() || !sum.isEmpty()){
            Toast.makeText(NewData.this, "Can't go to home screen with data entered!", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(intent);
        }
    }

    private void refreshActivity(){
        finish();
        startActivity(getIntent());
    }

    private EnteredData extractData(){
        String income = editTextIncome.getText().toString();
        String expenses = editTextExpenses.getText().toString();
        String dueDate = editTextDueDate.getText().toString();
        String sum = editTextTargetSum.getText().toString();

        return new EnteredData(income, expenses, dueDate, sum);
    }
}

