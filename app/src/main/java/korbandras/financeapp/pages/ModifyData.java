package korbandras.financeapp.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import korbandras.financeapp.R;
import korbandras.financeapp.xml.StoreAndLoadXML;

public class ModifyData extends Activity {
    private EditText modifyIncome;
    private EditText modifyExpenses;
    private EditText modifyDueDate;
    private EditText modifySum;
    private EditText savedSoFar;
    private Button modify;
    private Button goHome;
    private int entryId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);

        // Initialize views
        modifyIncome = findViewById(R.id.editTextIncomeModify);
        modifyExpenses = findViewById(R.id.editTextExpensesModify);
        modifyDueDate = findViewById(R.id.editTextDueDateModify);
        modifySum = findViewById(R.id.editTextTargetSumModify);
        savedSoFar = findViewById(R.id.savedSoFar);
        modify = findViewById(R.id.modifyButton);
        goHome = findViewById(R.id.Homemodify);

        Intent intent = getIntent();
        entryId = Integer.parseInt(intent.getStringExtra("id"));

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEntry();
            }
        });

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyData.this, FinanceApp.class);
                startActivity(intent);
            }
        });
    }

    private void updateEntry() {
        String income = modifyIncome.getText().toString();
        String expenses = modifyExpenses.getText().toString();
        String dueDate = modifyDueDate.getText().toString();
        String targetSum = modifySum.getText().toString();
        String SavedSoFar = savedSoFar.getText().toString();

        // Assuming you have a method in StoreAndLoadXML to update the entry by ID
        StoreAndLoadXML.updateEntryNew(getApplicationContext(), entryId, income, expenses, dueDate, targetSum, Integer.parseInt(SavedSoFar));

        // Optionally, navigate back to the previous screen or show a confirmation message
        Toast.makeText(this, "Entry updated",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ModifyData.this, Stats.class);
        intent.putExtra("targetSum", targetSum);
        intent.putExtra("savedSoFar", SavedSoFar);
        startActivity(intent);
    }
}
