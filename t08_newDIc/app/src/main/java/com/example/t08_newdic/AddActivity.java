package com.example.t08_newdic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    static final int RESULT_ADD_CODE = 2222;

    TextView word;
    Button quite;
    Button ok;
    Button save;
    EditText inputeng;
    EditText inputkor;
    String worddata[] = new String[2];

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
        Intent intent = getIntent();
        String temp = intent.getStringExtra("data");

        if(temp != null){
            String[] info = temp.split("\n");
            worddata[0] = info[0];
            worddata[1] = "";
            for(int i=1; i<info.length;i++){
                worddata[1] += info[i];
                if(i != info.length-1){
                    worddata[1] += "\n";
                }
            }
        }else{
            worddata[1] = "";
        }

        word.setText(worddata[1]);

    }

    public void show(){
        String data = "";
        if(!worddata[1].equals("")){
            data = worddata[1]+"\n";
        }

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
                show();
            }
            inputkor.setText("");
            inputeng.setText("");

        }else if(v.getId() == R.id.addsave){
            // 단어 저장할때 voca가 비었는지 안비었는지 확인하고 저장 들어갈것.
            if(voca.size() == 0){
                Toast.makeText(this,"저장된 단어가 없습니다.",Toast.LENGTH_LONG).show();
            }else{
                if(worddata[0] == null){
                    worddata[0] = voca.size()+"";
                }else{
                    worddata[0] = (Integer.parseInt(worddata[0]) + voca.size()) +"";
                    worddata[1] += "\n";
                }
                for(int i=0; i<voca.size(); i++){
                    worddata[1] += voca.get(i).eng +":" + voca.get(i).kor;
                    if(i != voca.size()-1){
                        worddata[1] += "\n";
                    }
                }

                Intent intent = new Intent();
                intent.putExtra("data",worddata[0] +"\n"+ worddata[1]);
                setResult(RESULT_ADD_CODE,intent);

                Toast.makeText(this,"단어 저장 완료",Toast.LENGTH_LONG).show();
            }

        }

    }
}
