package korbandras.financeapp.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import korbandras.financeapp.R;
import korbandras.financeapp.xml.Datas;
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
                updateEntry(entryId);
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

    private void updateEntry(int entryId) {
        String income = modifyIncome.getText().toString();
        String expenses = modifyExpenses.getText().toString();
        String dueDate = modifyDueDate.getText().toString();
        String targetSum = modifySum.getText().toString();
        String savedSoFarString = savedSoFar.getText().toString();

        // Fetch the original entry data
        Datas originalEntry = StoreAndLoadXML.getEntryById(getApplicationContext(), entryId);
        if (originalEntry == null) {
            // Handle the case where the entry is not found
            Toast.makeText(this, "Entry not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use original data if new data is not provided
        if (income.isEmpty()) income = originalEntry.getIncome();
        if (expenses.isEmpty()) expenses = originalEntry.getExpenses();
        if (dueDate.isEmpty()) dueDate = originalEntry.getDueDate();
        if (targetSum.isEmpty()) targetSum = originalEntry.getTargetSum();


        // Assuming saveEntry method updates or adds the entry in the XML
        StoreAndLoadXML.updateEntryNew(getApplicationContext(), entryId, income, expenses, dueDate, targetSum, Integer.parseInt(savedSoFarString));

        if (!savedSoFarString.isEmpty()) {
            // Confirmation and navigation
            Toast.makeText(this, "Entry updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ModifyData.this, Stats.class);
            intent.putExtra("targetSum", targetSum);
            intent.putExtra("savedSoFar", savedSoFarString);
            startActivity(intent);
        }
    }
}
