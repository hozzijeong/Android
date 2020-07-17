package com.example.newdic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    Button insert;
    EditText answer;
    TextView board,timmer,score,question;
    ProgressBar pro;
    int idx,sco;
    Button switchbtn;
    boolean turn = true;
    Random ran = new Random();
    int size = Storage.voca.size();

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
        timmer = findViewById(R.id.timemer);
        pro = findViewById(R.id.progressbar);

        switchbtn.setOnClickListener(this);
        insert.setOnClickListener(this);

        Log.d("size",size+"");
        if(size == 0){
            Toast.makeText(this,"저장된 단어가 없습니다",Toast.LENGTH_LONG).show();
            finish();
        }else {
            handler.sendEmptyMessageDelayed(0,1000);
            shuffle();
            show();
        }

    }

    int i=0;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(i<10){
                i++;
            }else{
                i = 0;
                idx +=1;
                show();
            }
            handler.sendEmptyMessageDelayed(0,1000);
            // 1번
            timmer.setText(i+"초");
        }
    };

    /*
       1. 단어장을 보여줄 때, 위에 시간초를 나타낸다.(최대 10초)
       2. 10초가 지나게 되면 단어가 다음 단어로 자동으로 넘어간다.
       3. 10초가 되기전에 단어를 "맞춘다면" 초가 초기화가 되면서 다음 단어로 넘어간다.
       4. 오답 처리시 시간이 초기화가 되야함
     */

    protected  void shuffle(){
        for(int i=0; i<1000; i++){
            int r = ran.nextInt(size);
            String tempeng = Storage.voca.get(0).eng;
            String tempkor = Storage.voca.get(0).kor;

            Storage.voca.get(0).eng = Storage.voca.get(r).eng;
            Storage.voca.get(r).eng = tempeng;

            Storage.voca.get(0).kor = Storage.voca.get(r).kor;
            Storage.voca.get(r).kor = tempkor;

        }
    }

    protected void show(){
        pro.setMax(size);
        pro.setProgress(idx);
        if(turn == true){
            if(idx < size){
                if(Storage.voca.get(idx).eng != null){
                    question.setText(Storage.voca.get(idx).eng);
                    board.setText((idx+1)+"/"+size);
                    String s = sco+"";
                    score.setText(s);
                    answer.setText("");
                }else{
                    board.setText("단어가 없음");
                }
            }else {
                Toast.makeText(this, "문제를 다 풀었습니다\n점수:" + sco, Toast.LENGTH_LONG).show();
                idx = 0;
                question.setText(Storage.voca.get(idx).eng);
                sco = 0;
                board.setText((idx+1)+ "/" + size);
                score.setText(sco + "");
                answer.setText("");
                timmer.setText("");
                pro.setProgress(idx);
                handler.removeMessages(0);
            }
        }else{
            if(idx < size){
                if(Storage.voca.get(idx).eng != null){
                    question.setText(Storage.voca.get(idx).kor);
                    board.setText((idx+1)+"/"+size);
                    String s = sco+"";
                    score.setText(s);
                    answer.setText("");
                }else{
                    board.setText("단어가 없음");
                }
            }else{
                Toast.makeText(this,"문제를 다 풀었습니다\n점수:"+sco,Toast.LENGTH_LONG).show();
                idx = 0;
                question.setText(Storage.voca.get(idx).kor);
                sco = 0;
                board.setText((idx+1)+"/"+size);
                score.setText(sco+"");
                answer.setText("");
                timmer.setText("");
                pro.setProgress(idx);
                handler.removeMessages(0);

            }
        }
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.insert){
            String ans = answer.getText().toString();
            if(ans.equals(Storage.voca.get(idx).kor) || ans.equals(Storage.voca.get(idx).eng)){
                sco +=10;
                Toast.makeText(this,"정답",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"OH...답",Toast.LENGTH_LONG).show();
            }
            i=0;
            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0,1000);
            idx +=1;
            show();
        }else if (v.getId() == R.id.switchbtn){
            if(turn == true){
                turn = false;
                idx = 0;
                pro.setProgress(idx);
                question.setText(Storage.voca.get(idx).kor);
                sco = 0;
                board.setText((idx+1)+"/"+size);
                score.setText(sco+"");
                answer.setText("");
                i=0;
                timmer.setText("");
            }else{
                turn = true;
                idx = 0;
                pro.setProgress(idx);
                question.setText(Storage.voca.get(idx).eng);
                sco = 0;
                board.setText((idx+1)+"/"+size);
                score.setText(sco+"");
                answer.setText("");
                i=0;
                timmer.setText("");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }
}
