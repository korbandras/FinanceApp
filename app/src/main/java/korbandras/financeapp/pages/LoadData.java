package korbandras.financeapp.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import korbandras.financeapp.R;

public class LoadData extends Activity {
    private TextView loadedData;
    private ListView listView;
    private Button addNew;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loaddata);

        addNew = findViewById(R.id.addNew);
        loadedData = findViewById(R.id.LoadedData);
        listView = findViewById(R.id.Percentage);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadData.this, NewData.class);
                startActivity(intent);
            }
        });
    }

    private void showError() {
        Toast.makeText(this, "Database is empty, please add data", Toast.LENGTH_SHORT).show();
        addNew.setVisibility(View.VISIBLE); // Show the "Add New" button
    }
}
