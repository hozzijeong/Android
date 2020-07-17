package com.example.t05_alertdialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.click).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.click){
            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            ab.setTitle("제목").setMessage("SubActivity로 이동?");

            ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"확인 클릭",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),com.example.t05_alertdialog.SubActivity.class);
                    startActivity(intent);
                }
            });

            ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"취소 클릭",Toast.LENGTH_LONG).show();
                }
            });
            ab.setCancelable(true);
            ab.show();

        }
    }
}
