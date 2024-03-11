package korbandras.financeapp.pages;

import android.app.Activity;
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
        modify = findViewById(R.id.modifyButton);
        goHome = findViewById(R.id.Homemodify);

        // Retrieve the ID passed from LoadData.java
        entryId = getIntent().getIntExtra("ENTRY_ID", -1); // Default to -1 if not found

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEntry();
            }
        });
    }

    private void updateEntry() {
        if (entryId == -1) {
            // Handle case where ID wasn't properly passed
            return;
        }
        String income = modifyIncome.getText().toString();
        String expenses = modifyExpenses.getText().toString();
        String dueDate = modifyDueDate.getText().toString();
        String targetSum = modifySum.getText().toString();

        // Assuming you have a method in StoreAndLoadXML to update the entry by ID
        StoreAndLoadXML.updateEntry(getApplicationContext(), entryId, income, expenses, dueDate, targetSum);

        // Optionally, navigate back to the previous screen or show a confirmation message
        Toast.makeText(this, "Entry updated",Toast.LENGTH_SHORT).show();
    }
}
