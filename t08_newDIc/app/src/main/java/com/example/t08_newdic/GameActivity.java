package com.example.t08_newdic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    static final int RESULT_RANK_CODE = 3333;

    String worddata; // MainActivity에서 합칠거임!
    Button back;
    Button insert;
    TextView board;
    TextView score;
    TextView question;
    EditText answer;

    ArrayList<Rank> rankdata;

    String[] eng;
    String[] kor;

    int SIZE = 0;
    int idx = 0;
    int sco =0;

    boolean turn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        back = findViewById(R.id.gameback);
        insert = findViewById(R.id.gameinsert);

        board = findViewById(R.id.board);
        score = findViewById(R.id.score);
        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);

        back.setOnClickListener(this);
        insert.setOnClickListener(this);

        findViewById(R.id.retry).setOnClickListener(this);
        findViewById(R.id.change).setOnClickListener(this);


        Intent intent = getIntent();
        worddata = intent.getStringExtra("data");

        if(worddata == null){
            Toast.makeText(this,"저장된 단어가 없습니다.",Toast.LENGTH_LONG).show();
            finish();
        }else{
            String[] info = worddata.split("\n"); // 단어 개수를 전부다 다눔.
            SIZE = Integer.parseInt(info[0]);
            int j = 1;
            eng = new String[SIZE];
            kor = new String[SIZE];
            for(int i=0; i<SIZE; i++){
                String[] temp = info[j].split(":");
                eng[i] = temp[0];
                kor[i] = temp[1]; // 여기 오류  ArrayIndexOutOfBoundsException: length=1; index=1 여기서 오류나서 Shuffle도 안됨,,,?
                j+=1;
            }
            shuffle();
            show();
        }
    }

    public void shuffle(){
        Random ran = new Random();
        for(int i=0; i<1000; i++) {
            int r = ran.nextInt(SIZE);
            String temp = eng[0];
            eng[0] = eng[r];
            eng[r] = temp;

            temp = kor[0];
            kor[0] = kor[r];
            kor[r] = temp;
        }
    }

    public void show(){
        if(idx < SIZE){
            question.setText(eng[idx]);
            board.setText((idx+1)+"/"+SIZE);
            score.setText(sco+"");
        }else{
            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            final EditText rank = new EditText(this);
            ab.setView(rank);
            ab.setTitle("메세지").setMessage("문제를 다 풀었습니다!\n점수:"+sco+"점");
            ab.setPositiveButton("아이디 입력", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    rankdata.add(new Rank(rank.getText().toString(),sco));
                    // rankdata에 값을 저장하고, finish할때 rankdata 값을 넘겨줌
                    }
            });

            ab.setNegativeButton("나가기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            ab.setNeutralButton("다시하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    retry();

                }
            });
            ab.setCancelable(false);
            ab.show();
        }
    }

    public void retry(){
        shuffle();
        idx = 0;
        sco = 0;
        answer.setText("");
        show();
    }

    public void change(){
        if(turn == true){
            String[] temp = eng;
            eng = kor;
            kor = temp;
            turn = false;
            show();
        }else{
            String[] temp = kor;
            kor = eng;
            eng = temp;
            turn = true;
            show();
        }
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("rank",rankdata);
        setResult(RESULT_RANK_CODE,intent);
        super.finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.gameinsert){
            String myAnswer = answer.getText().toString();
            if(myAnswer.equals(eng[idx]) || myAnswer.equals(kor[idx])){
                sco +=10;
                Toast.makeText(this,"정답",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"오답",Toast.LENGTH_LONG).show();
            }
            idx +=1;
            answer.setText("");
            show();
        }else if(v.getId() == R.id.gameback){
            finish();
        }else if(v.getId() == R.id.retry){
            retry();
        }else if(v.getId() == R.id.change){
            change();
        }
    }
}
