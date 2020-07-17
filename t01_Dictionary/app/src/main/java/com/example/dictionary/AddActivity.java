package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    TextView word;
    Button quite;
    Button ok;
    Button save;
    EditText inputeng;
    EditText inputkor;
    ArrayList<Voca> voca = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        quite = findViewById(R.id.addquite);
        quite.setOnClickListener(this);

        inputeng = findViewById(R.id.inputeng);
        inputkor = findViewById(R.id.inputkor);

        ok = findViewById(R.id.addok);
        ok.setOnClickListener(this);

        save = findViewById(R.id.addsave);
        save.setOnClickListener(this);

        word = findViewById(R.id.word);
    }

    public void show(){
        String data = "";
        for(int i=0; i<voca.size(); i++){
            data +=  voca.get(i).eng + ":"+voca.get(i).kor +"\n";
        }
        word.setText(data);
    }

    public int check(String eng){
        int check = -1;
        for(int i=0; i<voca.size(); i++){
            if(eng.equals(voca.get(i).eng)){
                check = i;
                break;
            }
        }
        if(check != -1){
            Toast.makeText(this,eng+"는 이미 존재하는 단어입니다.",Toast.LENGTH_LONG).show();
        }
        return check;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addquite){
            finish();
        }else if(v.getId() == R.id.addok) {
            String eng = inputeng.getText().toString();
            String kor = inputkor.getText().toString();
            int ck = check(eng);
            if (ck == -1) {
                voca.add(new Voca(eng, kor));
                Storatge.ENGDIC.add(eng);
                Storatge.KORDIC.add(kor);
                show();
            }
            inputkor.setText("");
            inputeng.setText("");
        }
    }
}
