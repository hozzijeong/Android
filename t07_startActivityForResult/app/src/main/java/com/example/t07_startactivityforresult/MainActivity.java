package com.example.t07_startactivityforresult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final int REQUEST_MAIN_CODE = 905;
    TextView input_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.go_sub).setOnClickListener(this);
        input_data = findViewById(R.id.input_data);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.go_sub){
            Intent intent = new Intent(this,com.example.t07_startactivityforresult.SubActivity.class);
            startActivityForResult(intent,REQUEST_MAIN_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Toast.makeText(getApplicationContext(),"수신 완료",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"수신 실패",Toast.LENGTH_LONG).show();
        }

        if(requestCode == REQUEST_MAIN_CODE && resultCode == RESULT_OK) {
            String value = data.getStringExtra("data");
            input_data.setText(value);
        }
    }
}
