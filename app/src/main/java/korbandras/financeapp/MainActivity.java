package korbandras.financeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button calculateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextIncome = findViewById(R.id.editTextIncome);
        editTextExpenses = findViewById(R.id.editTextExpenses);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        editTextTargetSum = findViewById(R.id.editTextTargetSum);
        calculateButton = (android.widget.Button) findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String income = editTextIncome.getText().toString();
                String expenses = editTextExpenses.getText().toString();
                String dueDate = editTextDueDate.getText().toString();
                String sum = editTextTargetSum.getText().toString();

                Intent intent = new Intent(MainActivity.this, Loading.class);

                intent.putExtra("Income",income);
                intent.putExtra("Expenses",expenses);
                intent.putExtra("DueDate",dueDate);
                intent.putExtra("Sum",sum);

                startActivity(intent);
            }
        });

    }
}

