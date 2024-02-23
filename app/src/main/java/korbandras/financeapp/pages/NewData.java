package korbandras.financeapp.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import korbandras.financeapp.sql_firstTry.DBManager;
import korbandras.financeapp.R;

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
                saveData();
            }
        });

    }

    private void saveData() {
        String income = editTextIncome.getText().toString();
        String expenses = editTextExpenses.getText().toString();
        String dueDate = editTextDueDate.getText().toString();
        String sum = editTextTargetSum.getText().toString();

        if(income.isEmpty() || expenses.isEmpty() || dueDate.isEmpty() || sum.isEmpty()){
            Toast.makeText(NewData.this, "Please fill all fields",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(NewData.this, Loading.class);

        intent.putExtra("Income",income);
        intent.putExtra("Expenses",expenses);
        intent.putExtra("DueDate",dueDate);
        intent.putExtra("Sum",sum);

        startActivity(intent);
    }
}

