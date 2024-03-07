package korbandras.financeapp.pages;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import korbandras.financeapp.R;

public class ModifyData extends Activity {
    private EditText modifyIncome;
    private EditText modifyExpenses;
    private EditText modifyDueDate;
    private EditText modifySum;
    private Button modify;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);

        modifyIncome = findViewById(R.id.editTextIncomeModify);
        modifyExpenses = findViewById(R.id.editTextExpensesModify);
        modifyDueDate = findViewById(R.id.editTextDueDateModify);
        modifySum = findViewById(R.id.editTextTargetSumModify);
        modify = (android.widget.Button) findViewById(R.id.modifyButton);

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
