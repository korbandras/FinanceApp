package korbandras.financeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Results extends Activity {
    private TextView results1;
    private TextView results2;
    private TextView results3;
    private Button returnhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        results1 = findViewById(R.id.textViewResult);
        results2 = findViewById(R.id.textViewResult2);
        results3 = findViewById(R.id.textViewResult3);
        returnhome = findViewById(R.id.returnHome);

        Intent intent = getIntent();
        String receivedIncome = intent.getStringExtra("Income");
        String receivedExpenses = intent.getStringExtra("Expenses");
        String receivedDueDate = intent.getStringExtra("DueDate");
        String receivedSum = intent.getStringExtra("Sum");

        double income = Double.parseDouble(receivedIncome);
        double expenses = Double.parseDouble(receivedExpenses);
        int dueDate = Integer.parseInt(receivedDueDate);
        double targetSum = Double.parseDouble(receivedSum);
        double netpositive = income - expenses;

        if(targetSum / dueDate >= netpositive || income < expenses || dueDate <= 0 || targetSum <= 0){
            results1.setText("Not possible");
        }
        else{
            double permonth = targetSum / dueDate;
            double max = netpositive;
            double months = targetSum / max;

            results1.setText(String.format("Save a minimum of $%.2f per month to achieve goal.", permonth));
            results2.setText(String.format("Maximum save available in a month is $%.2f.", max));
            results3.setText(String.format("By saving the max amount per month you can achieve your goal in %.1f months" +
                    " compared to %d month(s) given.", months, dueDate));
        }

        returnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Results.this, Decide.class);
                startActivity(intent);
            }
        });
    }
}
