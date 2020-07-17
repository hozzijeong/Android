package com.example.newdic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    Button insert;
    TextView question;
    EditText answer;
    TextView score;
    TextView board;
    int idx;
    int sco = 0;
    Button switchbtn;
    boolean turn = true;
    Random ran = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        insert = findViewById(R.id.insert);
        answer = findViewById(R.id.answer);
        question = findViewById(R.id.question);
        score = findViewById(R.id.score);
        board = findViewById(R.id.board);
        switchbtn = findViewById(R.id.switchbtn);

        switchbtn.setOnClickListener(this);
        insert.setOnClickListener(this);

        idx =0;
        Log.d("size",Storage.eng.size()+"");

        if(Storage.eng.size() == 0){
            Toast.makeText(this,"저장된 단어가 없습니다",Toast.LENGTH_LONG).show();
            finish();
        }else {
            shuffle();
            show();
        }
    }

    protected  void shuffle(){
        for(int i=0; i<1000; i++){
            int r = ran.nextInt(Storage.eng.size());
            String tempeng = Storage.eng.get(0);
            String tempkor = Storage.kor.get(0);

            Storage.eng.set(0,Storage.eng.get(r));
            Storage.eng.set(r,tempeng);

            Storage.kor.set(0,Storage.kor.get(r));
            Storage.kor.set(r,tempkor);

        }
    }

    protected void show(){
        if(turn == true){
            if(idx < Storage.eng.size()){
                if(Storage.eng.get(idx) != null){
                    question.setText(Storage.eng.get(idx));
                    board.setText((idx+1)+"/"+Storage.kor.size());
                    String s = sco+"";
                    score.setText(s);
                    answer.setText("");
                }else{
                    board.setText("단어가 없음");
                }
            }else {
                Toast.makeText(this, "문제를 다 풀었습니다\n점수:" + sco, Toast.LENGTH_LONG).show();
                idx = 0;
                question.setText(Storage.eng.get(idx));
                sco = 0;
                board.setText((idx+1)+ "/" + Storage.eng.size());
                score.setText(sco + "");
                answer.setText("");
            }
        }else{
            if(idx < Storage.eng.size()){
                if(Storage.eng.get(idx) != null){
                    question.setText(Storage.kor.get(idx));
                    board.setText((idx+1)+"/"+Storage.eng.size());
                    String s = sco+"";
                    score.setText(s);
                    answer.setText("");
                }else{
                    board.setText("단어가 없음");
                }
            }else{
                Toast.makeText(this,"문제를 다 풀었습니다\n점수:"+sco,Toast.LENGTH_LONG).show();
                idx = 0;
                question.setText(Storage.kor.get(idx));
                sco = 0;
                board.setText((idx+1)+"/"+Storage.eng.size());
                score.setText(sco+"");
                answer.setText("");
            }

        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.insert){
            String ans = answer.getText().toString();
            if(ans.equals(Storage.kor.get(idx)) || ans.equals(Storage.eng.get(idx))){
                sco +=10;
                Toast.makeText(this,"정답",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"OH...답",Toast.LENGTH_LONG).show();
            }
            idx +=1;
            show();
        }else if (v.getId() == R.id.switchbtn){
            if(turn == true){
                turn = false;
                idx = 0;
                question.setText(Storage.kor.get(idx));
                sco = 0;
                board.setText((idx+1)+"/"+Storage.eng.size());
                score.setText(sco+"");
                answer.setText("");
            }else{
                turn = true;
                idx = 0;
                question.setText(Storage.eng.get(idx));
                sco = 0;
                board.setText((idx+1)+"/"+Storage.eng.size());
                score.setText(sco+"");
                answer.setText("");
            }
        }
    }
}
