package com.example.adriana.piggybank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView currentTv;
    Button oneBttn;
    Button fiveBttn;
    Button tenBttn;
    Button withdrawBttn;
    Button depositBttn;
    EditText amountEt;
    float amount = 0;
    float currentBalance = 0;
    SharedPreferences myPrefs;
    boolean creating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext(); // app level storage
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        final SharedPreferences.Editor peditor = myPrefs.edit();
        //peditor.putFloat("currentBalance", currentBalance);
        //peditor.commit();


        amountEt = (EditText) findViewById(R.id.amountEt);

        currentTv = (TextView) findViewById(R.id.currentTv);
        oneBttn = (Button) findViewById(R.id.oneBttn);
        oneBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    amount = Float.valueOf(amountEt.getText().toString());
                } catch(Exception e) {
                    amount = 0;
                }
                amount += 1;
                amountEt.setText(String.format("%.02f", amount));
            }
        });
        fiveBttn = (Button) findViewById(R.id.fiveBttn);
        fiveBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    amount = Float.valueOf(amountEt.getText().toString());
                } catch(Exception e) {
                    amount = 0;
                }
                amount += 5;
                amountEt.setText(String.format("%.02f", amount));
            }
        });

        tenBttn = (Button) findViewById(R.id.tenBttn);
        tenBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    amount = Float.valueOf(amountEt.getText().toString());
                } catch(Exception e) {
                    amount = 0;
                }
                amount += 10;
                amountEt.setText(String.format("%.02f", amount));
            }
        });
        withdrawBttn = (Button) findViewById(R.id.withdrawBttn);
        withdrawBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = amountEt.getText().toString();
                if (!text.equals("")) {
                    float withdraw = currentBalance - Float.valueOf(text);
                    if (withdraw < 0) {
                        Toast.makeText(getApplicationContext(), "Not enough funds", Toast.LENGTH_SHORT).show();
                        amountEt.setText("0.00");
                        amountEt.setHint("0.00");
                    } else {
                        Intent intent = new Intent(MainActivity.this, ConfirmationActivity2.class);

                        peditor.putFloat("expectedBalance", withdraw);
                        peditor.commit();
                        startActivity(intent);
                    }
                }
            }
        });
        depositBttn = (Button) findViewById(R.id.depositBttn);
        depositBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = amountEt.getText().toString();
                if (!text.equals("")) {
                    Intent intent = new Intent(MainActivity.this, ConfirmationActivity2.class);
                    float deposit = currentBalance + Float.valueOf(text);
                    //intent.putExtra("expectedBalance", deposit);
                    peditor.putFloat("expectedBalance", deposit);
                    peditor.commit();
                    startActivity(intent);
                }
            }
        });

        creating = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        final SharedPreferences.Editor peditor = myPrefs.edit();
        currentBalance = Float.valueOf(currentTv.getText().toString());
        peditor.putFloat("currentBalance", currentBalance);
        String text = amountEt.getText().toString();
        if (!text.equals("")) {
            float deposit = Float.valueOf(text);
            peditor.putFloat("deposit", deposit);
        }
        peditor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!creating) {
            amountEt.setText(String.format("%.02f", myPrefs.getFloat("deposit", 0)));
        } else {
            amountEt.setText("0.00");
            amountEt.setHint("0.00");
            creating = false;
        }
        amount = 0;
        currentBalance = myPrefs.getFloat("currentBalance", 0);
        currentTv.setText(String.format("%.02f", currentBalance));

    }
}
