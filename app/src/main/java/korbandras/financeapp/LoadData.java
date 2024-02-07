package korbandras.financeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoadData extends Activity {
    private TextView loadedData;
    private TextView percentage;
    private Button addNew;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loaddata);

        addNew = findViewById(R.id.addNew);
        loadedData = findViewById(R.id.LoadedData);
        percentage = findViewById(R.id.Percentage);



        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadData.this, NewData.class);
                startActivity(intent);
            }
        });
    }
}
