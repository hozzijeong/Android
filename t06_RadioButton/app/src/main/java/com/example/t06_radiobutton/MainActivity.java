package com.example.t06_radiobutton;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RadioButton rbtn1;
    RadioButton rbtn2;
    RadioButton rbtn3;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rbtn1 = findViewById(R.id.rbtn1);
        rbtn2 = findViewById(R.id.rbtn2);
        rbtn3 = findViewById(R.id.rbtn3);

        layout = findViewById(R.id.layout);

        rbtn1.setChecked(true);
        rbtn2.setChecked(false);
        rbtn3.setChecked(true);

        rbtn3.setOnClickListener(this);
        rbtn2.setOnClickListener(this);
        rbtn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(rbtn1.isChecked()){
            layout.setBackgroundColor(Color.parseColor("#ff0000"));
        }else if(rbtn2.isChecked()){
            layout.setBackgroundColor(Color.parseColor("#FFE400"));
        }else if(rbtn3.isChecked()){
            layout.setBackgroundColor(Color.parseColor("#0054FF"));
        }
    }
}
