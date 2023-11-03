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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextIncome = findViewById(R.id.editTextIncome);
        editTextExpenses = findViewById(R.id.editTextExpenses);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        editTextTargetSum = findViewById(R.id.editTextTargetSum);
        textViewResult = findViewById(R.id.textViewResult);
    }

    public void calculateMonthlySavings(View view) {

        double income = Double.parseDouble(editTextIncome.getText().toString());
        double expenses = Double.parseDouble(editTextExpenses.getText().toString());
        int dueDate = Integer.parseInt(editTextDueDate.getText().toString());
        double targetSum = Double.parseDouble(editTextTargetSum.getText().toString());

        if (income <= expenses || dueDate <= 0 || targetSum <= 0) {
            textViewResult.setText("It's not possible");
        } else {
            double monthlySavings = (targetSum / dueDate) - (expenses - income);
            if (monthlySavings <= 0) {
                textViewResult.setText("It's not possible");
            } else {
                textViewResult.setText("Save $" + String.format("%.2f", monthlySavings) + " monthly to reach your target by the due date.");
            }
        }
    }
}