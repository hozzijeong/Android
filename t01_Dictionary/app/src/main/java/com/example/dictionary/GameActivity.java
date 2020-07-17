package com.example.dictionary;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.Random;

// 한꺼번에 등장하는 것과, 차례대로 등장하느 것도 같이 해보기.


public class GameActivity extends AppCompatActivity implements View.OnClickListener  {
    Button back;
    Button insert;
    TextView board;
    TextView score;
    TextView question;
    EditText answer;
    int idx;
    int sco = 0;
    int SIZE = Storatge.ENGDIC.size();

    Boolean turn = true;

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

        idx = 0;
        if(SIZE == 0){
            Toast.makeText(this,"저장된 단어가 없습니다...",Toast.LENGTH_LONG).show();
            finish();
        }else{
            shuffle();
            show();
        }
    }

    public void show(){
        if(idx < Storatge.KORDIC.size()){
            question.setText(Storatge.ENGDIC.get(idx));
            board.setText((idx+1)+"/"+SIZE);
            score.setText(sco+"");
        }else{
            Toast.makeText(this,"문제를 다 풀었습니다.\n점수는"+sco+"점입니다.",Toast.LENGTH_LONG).show();
            answer.setText("");
        }
    }

    public void reset(){
        shuffle();
        idx = 0;
        board.setText((idx+1)+"/"+SIZE);
        question.setText(Storatge.ENGDIC.get(idx));
        answer.setText("");
        sco = 0;
        score.setText(sco+"");

    }

    public void shuffle(){
        Random ran = new Random();
        for(int i=0; i<1000; i++){
            int r  = ran.nextInt(SIZE);

            String temp = Storatge.ENGDIC.get(0);
            Storatge.ENGDIC.set(0,Storatge.ENGDIC.get(r));
            Storatge.ENGDIC.set(r,temp);

            temp = Storatge.KORDIC.get(0);
            Storatge.KORDIC.set(0,Storatge.KORDIC.get(r));
            Storatge.KORDIC.set(r,temp);
        }
    }

    public void change(){
        if(turn == true){
            ArrayList<String> temp  = Storatge.KORDIC;
            Storatge.KORDIC = Storatge.ENGDIC;
            Storatge.ENGDIC = temp;
            turn = false;
            show();
        }else{
            ArrayList<String> temp  = Storatge.KORDIC;
            Storatge.KORDIC = Storatge.ENGDIC;
            Storatge.ENGDIC = temp;
            turn = true;
            show();
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.gameinsert){
            String myAnswer = answer.getText().toString();

            if(myAnswer.equals(Storatge.KORDIC.get(idx))){
                Toast.makeText(this,"정답!",Toast.LENGTH_LONG).show();
                sco = sco + 10;
            }else{
                Toast.makeText(this,"오답!",Toast.LENGTH_LONG).show();
            }
            idx +=1;
            answer.setText("");
            show();
        }else if(v.getId() == R.id.gameback){
            finish();
        }else if(v.getId() == R.id.retry){
            reset();
        }else if(v.getId() == R.id.change){
            change();
        }
    }
}
