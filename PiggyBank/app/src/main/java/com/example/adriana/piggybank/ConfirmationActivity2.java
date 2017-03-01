package com.example.adriana.piggybank;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmationActivity2 extends AppCompatActivity {

    SharedPreferences myPrefs;
    float amount;
    Button cancelBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context context = getApplicationContext();
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        amount = myPrefs.getFloat("expectedBalance", 0);

        TextView balanceTv = (TextView) findViewById(R.id.balanceTv);
//        final float amount = getIntent().getFloatExtra("BALANCE", 0);
        balanceTv.setText(String.format("%.02f", amount));

        Button acceptBt = (Button) findViewById(R.id.acceptBt);
        acceptBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor peditor = myPrefs.edit();
                peditor.putFloat("deposit", (float) 0);
                peditor.putFloat("currentBalance", amount);
                peditor.commit();
                finish();
            }
        });
        cancelBt = (Button) findViewById(R.id.cancelBttn);
        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
