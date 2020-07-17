package com.example.newdic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.invoke.VolatileCallSite;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Voca> vocaArr = new ArrayList<>();
    TextView word;
    Button quite;
    Button ok;
    EditText inputeng;
    EditText inputkor;
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

        word = findViewById(R.id.word);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addquite){
            finish();
        }else if(v.getId() == R.id.addok){
            String data = "";
            String eng = inputeng.getText().toString();
            String kor = inputkor.getText().toString();
            Storage.eng.add(eng);
            Storage.kor.add(kor);

            Voca a = new Voca(eng,kor);
            a.setEng(eng);
            a.setKor(kor);
            vocaArr.add(a);
            for(int i =0; i<vocaArr.size(); i++){
                data = data + vocaArr.get(i).getEng()+ ":" + vocaArr.get(i).getKor() + "\n";
            }
            word.setText(data);
            inputeng.setText("");
            inputkor.setText("");
        }
    }
}
