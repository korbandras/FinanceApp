package korbandras.financeapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends Activity {
    private EditText editTextIncome;
    private EditText editTextExpenses;
    private EditText editTextDueDate;
    private EditText editTextTargetSum;
    private TextView textViewResult;
    private TextView textViewResult2;
    private TextView textViewResult3;
    private Switch switchDarkMode;

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
        textViewResult3 = findViewById(R.id.textViewResult3);

        switchDarkMode = findViewById(R.id.switchDarkMode);
        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Enable dark mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    // Disable dark mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

    public void calculateMonthlySavings(View view) {

        double income = Double.parseDouble(editTextIncome.getText().toString());
        double expenses = Double.parseDouble(editTextExpenses.getText().toString());
        int dueDate = Integer.parseInt(editTextDueDate.getText().toString());
        double targetSum = Double.parseDouble(editTextTargetSum.getText().toString());
        double netpositive = income - expenses;

        if(targetSum / dueDate >= netpositive || income < expenses || dueDate <= 0 || targetSum <= 0){
            textViewResult.setText("Not possible");
        }
        else{
            double permonth = targetSum / dueDate;
            double max = netpositive;
            double months = targetSum / max;

            textViewResult.setText(String.format("Save a minimum of $%.2f per month to achieve goal.", permonth));
            textViewResult2.setText(String.format("Maximum save available in a month is $%.2f.", max));
            textViewResult3.setText(String.format("By saving the max amount per month you can achieve your goal in %.1f months" +
                    " compared to %d months given.", months, dueDate));
        }
    }
}
