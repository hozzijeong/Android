package com.example.newdic;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView title;
    Button quite;
    Button add;
    Button game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);

        add = findViewById(R.id.addbtn);
        quite = findViewById(R.id.quitebtn);
        game = findViewById(R.id.gamebtn);

        add.setOnClickListener(this);
        quite.setOnClickListener(this);
        game.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addbtn){
            Intent intent = new Intent(this,com.example.newdic.AddActivity.class);
            startActivityForResult(intent,3000);
        }else if(v.getId() == R.id.gamebtn){
            Intent intent = new Intent(this,com.example.newdic.GameActivity.class);
            startActivityForResult(intent,500);
        }else if(v.getId() == R.id.quitebtn){
            finish();
        }
    }
}
