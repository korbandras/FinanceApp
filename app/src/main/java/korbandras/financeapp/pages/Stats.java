package korbandras.financeapp.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import korbandras.financeapp.R;

public class Stats extends Activity {
    private TextView target;
    private TextView soFar;
    private TextView progressText;
    private ProgressBar progressBar;
    private Button statsHome;
    private Button statsLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);

        target = findViewById(R.id.statsTarget);
        soFar = findViewById(R.id.statsSoFar);
        progressText = findViewById(R.id.statsProgressText);
        progressBar = findViewById(R.id.statsProgress);
        statsHome = findViewById(R.id.statsHome);
        statsLoad = findViewById(R.id.statsLoad);

        Intent received = getIntent();
        String receivedTarget = received.getStringExtra("targetSum");
        String receivedSaved = received.getStringExtra("savedSoFar");

        setStats(receivedTarget, receivedSaved);

        statsHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stats.this, FinanceApp.class);
                startActivity(intent);
            }
        });

        statsLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stats.this, LoadData.class);
                startActivity(intent);
            }
        });
    }

    private void setStats(String receivedTarget, String receivedSaved) {
        int targetSum = Integer.parseInt(receivedTarget);
        int savedSoFar = Integer.parseInt(receivedSaved);
        double percent = (double) savedSoFar/targetSum*100;

        target.setText(String.format("Your target was to save $%d.", targetSum));
        progressBar.setProgress((int) percent);
        progressText.setText(percent + "%");
        soFar.setText(String.format("You saved $%d so far", savedSoFar));
    }
}
