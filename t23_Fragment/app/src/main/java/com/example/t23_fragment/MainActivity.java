package com.example.t23_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1,btn2,btn3;

    Fragment1 fragment1 = new Fragment1();
    Fragment2 fragment2 = new Fragment2();
    Fragment3 fragment3 = new Fragment3();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(v.getId() == R.id.btn1){
            ft.replace(R.id.fragment,fragment1);
        }else if(v.getId() == R.id.btn2){
            ft.replace(R.id.fragment,fragment2);
        }else if(v.getId() == R.id.btn3){
            ft.replace(R.id.fragment,fragment3);
        }
        ft.commit();
    }

    public void replaceFra(int num){
        if(num == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment2).commit();
        } else if (num == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment3).commit();

        }else if(num ==3){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment1).commit();
        }
    }



}
