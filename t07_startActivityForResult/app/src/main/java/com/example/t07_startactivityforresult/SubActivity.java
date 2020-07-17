package com.example.t07_startactivityforresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity implements View.OnClickListener {
    EditText data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        findViewById(R.id.back).setOnClickListener(this);
        data = findViewById(R.id.data);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.back){
            Intent intent = new Intent();
            String value = data.getText().toString();
            intent.putExtra("data",value);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
