package korbandras.financeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Decide extends Activity {
    private Button loadData;
    private Button newData;
    private FinanceData dataSouce;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decide);

        loadData = findViewById(R.id.LoadData);
        newData = findViewById(R.id.NewData);

        dataSouce = new FinanceData(this);

        newData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Decide.this, NewData.class);
                startActivity(intent);
            }
        });

        loadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Decide.this, LoadData.class);
                startActivity(intent2);
            }
        });
    }

}

