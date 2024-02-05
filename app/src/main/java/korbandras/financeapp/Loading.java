package korbandras.financeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Loading extends Activity {
    private ProgressBar progressBar;
    private TextView loadingText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        progressBar = findViewById(R.id.progressBar);
        loadingText = findViewById(R.id.loadingText);
        Intent intent = getIntent();
        String receivedIncome = intent.getStringExtra("Income");
        String receivedExpenses = intent.getStringExtra("Expenses");
        String receivedDueDate = intent.getStringExtra("DueDate");
        String receivedSum = intent.getStringExtra("Sum");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String income = receivedIncome;
                String expenses = receivedExpenses;
                String dueDate = receivedDueDate;
                String sum = receivedSum;

                Intent intent = new Intent(Loading.this, Results.class);

                intent.putExtra("Income",income);
                intent.putExtra("Expenses",expenses);
                intent.putExtra("DueDate",dueDate);
                intent.putExtra("Sum",sum);

                startActivity(intent);
                finish();
            }
        },5000);
    }
}
