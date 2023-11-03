package korbandras.financeapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    private EditText editTextIncome;
    private EditText editTextExpenses;
    private EditText editTextDueDate;
    private EditText editTextTargetSum;
    private TextView textViewResult;
    private TextView textViewResult2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextIncome = findViewById(R.id.editTextIncome);
        editTextExpenses = findViewById(R.id.editTextExpenses);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        editTextTargetSum = findViewById(R.id.editTextTargetSum);
        textViewResult = findViewById(R.id.textViewResult);
        textViewResult2 = findViewById(R.id.textViewResult2);
    }

    public void calculateMonthlySavings(View view) {

        double income = Double.parseDouble(editTextIncome.getText().toString());
        double expenses = Double.parseDouble(editTextExpenses.getText().toString());
        int dueDate = Integer.parseInt(editTextDueDate.getText().toString());
        double targetSum = Double.parseDouble(editTextTargetSum.getText().toString());
        double netpositive = income - expenses;
        double permonth;

        if(targetSum / dueDate >= netpositive || income < expenses || dueDate <= 0 || targetSum <= 0){
            textViewResult.setText("Not possible");
        }
        else{
            permonth = targetSum / dueDate;
            textViewResult.setText(String.format("Save monthly $%.2f to achive goal", permonth));
        }

        //textViewResult.setText(String.format("%.2f", netpositive));
        //textViewResult2.setText(String.format("%.2f", permonth));
    }
}
