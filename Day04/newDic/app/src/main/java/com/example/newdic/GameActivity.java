package com.example.newdic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    Button insert,switchbtn,start,back;
    EditText answer;
    TextView board,timer,score,question;
    ProgressBar pro;
    int idx,sco,max;
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
        timer = findViewById(R.id.timer);
        pro = findViewById(R.id.progressbar);
        start = findViewById(R.id.startbtn);
        back = findViewById(R.id.backbtn);

        answer.setVisibility(View.INVISIBLE);

        switchbtn.setOnClickListener(this);
        insert.setOnClickListener(this);
        start.setOnClickListener(this);
        back.setOnClickListener(this);

        Log.d("size",Storage.voca.size()+"");
        max = Storage.voca.size();
        if(max == 0){
            Toast.makeText(this,"저장된 단어가 없습니다",Toast.LENGTH_LONG).show();
            finish();
        }else {
            shuffle();
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
                Toast.makeText(getApplicationContext(),"시간초과",Toast.LENGTH_LONG).show();
                show();
            }
            handler.sendEmptyMessageDelayed(0,1000);
            // 1번
            timer.setText(i+"초");
        }
    };

    /*
       1. 단어장을 보여줄 때, 위에 시간초를 나타낸다.(최대 10초)
       2. 10초가 지나게 되면 단어가 다음 단어로 자동으로 넘어간다.
       3. 10초가 되기전에 단어를 "맞춘다면" 초가 초기화가 되면서 다음 단어로 넘어간다.
       4. 오답 처리시 시간이 초기화가 되야함
     */

    protected void registerRank(){
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        final EditText et = new EditText(getApplicationContext());
        final int score = sco; // final로 지정하는 이유가 따로 있나?

        ab.setTitle("랭킹 등록").setMessage("이름을 입력하세요");
        ab.setView(et);
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = et.getText().toString();
                Log.d("mood","score"+score);
                Storage.rank.add(new Rank(name,score));
                addRank(name,score);
                Toast.makeText(getApplicationContext(),"등록 완료",Toast.LENGTH_SHORT).show();
            }
        });
        ab.setCancelable(true);
        ab.show();
    }

    protected void addRank(String name, int score){
        SQLiteDatabase db = openOrCreateDatabase("Word.db",MODE_PRIVATE,null);
        db.execSQL("INSERT INTO rank_list(name,score) VALUES('"+name+"','"+score+"')");
        db.close();
    }

    public void reset(){
        i = 0;
        idx = 0;
        question.setText(Storage.voca.get(idx).eng);
        sco = 0;
        board.setText((idx+1)+ "/" + max);
        score.setText(sco + "");
        answer.setText("");
        timer.setText("");
        pro.setProgress(idx);
        answer.setVisibility(View.INVISIBLE);
        handler.removeMessages(0);
    }

    protected  void shuffle(){
        for(int i=0; i<1000; i++){
            int r = ran.nextInt(Storage.voca.size());
            String tempeng = Storage.voca.get(0).eng;
            String tempkor = Storage.voca.get(0).kor;

            Storage.voca.get(0).eng =Storage.voca.get(r).eng;
            Storage.voca.get(r).eng =tempeng;

            Storage.voca.get(0).kor =Storage.voca.get(r).kor;
            Storage.voca.get(r).kor =tempkor;

        }
    }

    protected void show(){
        pro.setMax(max);
        pro.setProgress(idx);
        if(turn == true){
            if(idx < max){
                if(Storage.voca.get(idx).eng != null){
                    question.setText(Storage.voca.get(idx).eng);
                    board.setText((idx+1)+"/"+max);
                    String s = sco+"";
                    score.setText(s);
                    answer.setText("");
                }else{
                    board.setText("단어가 없음");
                }
            }else {
                registerRank();
                Toast.makeText(this, "문제를 다 풀었습니다\n점수:" + sco, Toast.LENGTH_LONG).show();
                reset();
            }
        }else{
            if(idx < max){
                if(Storage.voca.get(idx).eng != null){
                    question.setText(Storage.voca.get(idx).kor);
                    board.setText((idx+1)+"/"+max);
                    String s = sco+"";
                    score.setText(s);
                    answer.setText("");
                }else{
                    board.setText("단어가 없음");
                }
            }else{
                registerRank();
                Toast.makeText(this,"문제를 다 풀었습니다\n점수:"+sco,Toast.LENGTH_LONG).show();
                reset();
            }
        }
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.startbtn){
            handler.sendEmptyMessageDelayed(0,1000);
            answer.setVisibility(View.VISIBLE);
            show();
        }

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
            if(turn == true){turn = false;
            }else{ turn = true;}
            reset();
            show();
        }

        if(v.getId() == R.id.backbtn){
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();;
        handler.removeMessages(0);
    }
}
