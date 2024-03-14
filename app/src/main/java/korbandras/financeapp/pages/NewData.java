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
import korbandras.financeapp.xml.StoreAndLoadXML;

public class NewData extends Activity {
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
                saveNew();
            }
        });

    }

    private void saveData(String income, String expenses, String dueDate, String sum) {

        Datas data = new Datas(income, expenses, dueDate, sum);

        List<Datas> dataList = new ArrayList<>();

        if(income.isEmpty() || expenses.isEmpty() || dueDate.isEmpty() || sum.isEmpty()){
            Toast.makeText(NewData.this, "Please fill all fields",Toast.LENGTH_SHORT).show();
            return;
        }

        dataList.add(data);

        StoreAndLoadXML.saveXML(getApplicationContext(),dataList);

        Intent intent = new Intent(NewData.this, Loading.class);
        intent.putExtra("Income",income);
        intent.putExtra("Expenses",expenses);
        intent.putExtra("DueDate",dueDate);
        intent.putExtra("Sum",sum);
        startActivity(intent);
    }


    private void saveNew(){
        String income = editTextIncome.getText().toString();
        String expenses = editTextExpenses.getText().toString();
        String dueDate = editTextDueDate.getText().toString();
        String sum = editTextTargetSum.getText().toString();
        double netpos = Integer.parseInt(income) - Integer.parseInt(expenses);
        if((double) Integer.parseInt(sum) / Integer.parseInt(dueDate) > netpos || Integer.parseInt(income) < Integer.parseInt(expenses) || Integer.parseInt(dueDate) <= 0 || Integer.parseInt(sum) <= 0){
            Toast.makeText(NewData.this, "Not possible, won't be saved",Toast.LENGTH_SHORT).show();
            return;
        }else{
            saveData(income, expenses, dueDate, sum);
        }
    }
}

