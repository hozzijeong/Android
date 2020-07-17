package com.example.t11_textrpg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String message;
    TextView Title_title,Title_message;
    EditText Title_input;
    Button start,quite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainGame();
        StageTitle st = new StageTitle();
        Title_title = findViewById(R.id.Title_title);
        Title_message = findViewById(R.id.Title_message);
        Title_input = findViewById(R.id.Title_input);
        start = findViewById(R.id.Title_start);
        quite = findViewById(R.id.Title_quite);

        start.setOnClickListener(this);
        quite.setOnClickListener(this);

        Title_title.setText(st.Title_title);
        Title_message.setText(st.Title_message);
        Title_input.setHint(st.Title_message);
    }

    void MainGame(){
        GameManager gameManager = new GameManager();
        boolean run = true;
        while(true) {
            run = gameManager.changeStage();
            if(run == false) {
                break;
            }
        }
        message = "게임 오버";
    }

    @Override
    public void onClick(View v) {

    }
}
