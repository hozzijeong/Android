package com.example.card_game_make_a_pair;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    TextView timer;
    Button start,quite,reset;
    Button[][] arr = new Button[4][6];
    int cnt = 0;
    int[] num = new int[24];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        timer = findViewById(R.id.game_timer);
        start = findViewById(R.id.game_start);
        quite = findViewById(R.id.game_quite);
        reset = findViewById(R.id.game_reset);

        start.setOnClickListener(this);
        quite.setOnClickListener(this);
        reset.setOnClickListener(this);
        set_btn(); // 버튼 생성 및 연결
        set_num(); // 숫자 생성
    }

    public void set_btn(){
        for(int i=0; i<arr.length;i++){
            for(int j=0; j<arr[i].length;j++){
                arr[i][j] = findViewById(R.id.game_btn1_1+((i*6)+j));
                arr[i][j].setOnClickListener(this);
                arr[i][j].setEnabled(false);
            }
        }
    }

    public void set_num(){
        for(int i=0; i<num.length; i++){
            num[i] = (i/2)+1;
//            Log.d("mood","num["+i+"]"+num[i]);
        }
    }

    public void shuffle_num(){
        Random ran = new Random();
        for(int i=0; i<500; i++){
            int r = ran.nextInt(num.length);
            int temp = num[0];
            num[0] = num[r];
            num[r] = temp;
        }
    }

    public void insert_num(){
        shuffle_num();
        int k=0;
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[i].length; j++){
                arr[i][j].setText(num[k]+"");
                k++;
            }
        }
    }

    void reset_num(){
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[i].length; j++){
                arr[i][j].setText("");
                arr[i][j].setEnabled(true);
            }
        }
    }

    void game_reset(){
        visible_time = 10;
        limit_time = 20;
        turn = true;
        temp[0] = temp[1] = 0;
        ai=aj=bi=bj= 0;
        check = -1;
        cnt = 0;
        timer.setText("00:00.00");
        shuffle_num();
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[i].length; j++){
                arr[i][j].setText("");
                arr[i][j].setEnabled(false);
            }
        }
        handler.removeMessages(0);
        handler.removeMessages(1);
        handler.removeMessages(2);
    }

    void end_message(final String score){
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("게임 종료").setMessage("아이디 입력: ");
        final EditText et = new EditText(getApplicationContext());
        ab.setView(et);
        ab.setPositiveButton("입력", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.user_infos.add(new User_info(et.getText().toString(),score));
                add_db(et.getText().toString(),score);
                load_data();
                Toast.makeText(getApplicationContext(),et.getText().toString()+"님 점수:"+score,
                        Toast.LENGTH_LONG).show();
            }
        });
        ab.setCancelable(true);
        ab.show();
    }

    private void load_data(){
        SQLiteDatabase db = openOrCreateDatabase("Card.db",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT * FROM rank_list",null);
        c.moveToFirst();
        MainActivity.user_infos.clear();
        while (c.isAfterLast() == false){
            MainActivity.user_infos.add(
                    new User_info(c.getString(1),c.getString(2)));
            c.moveToNext();
        }
        c.close();
        db.close();
    }

    private void add_db(String name, String score){
        SQLiteDatabase db = openOrCreateDatabase("Card.db",MODE_PRIVATE,null);
        db.execSQL("INSERT INTO rank_list(name,score) VALUES('"+name+"','"+score+"')");
        db.close();
    }

    float visible_time = 10;
    float limit_time = 20;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                if(visible_time > 0){
                    visible_time = (float)(visible_time-0.01);
                    String time = String.format("%.2f",visible_time);
                    timer.setText(time);
                    handler.sendEmptyMessageDelayed(0,10);
                }else{
                    check = 1;
                    reset_num();
                    removeMessages(0);
                    handler.sendEmptyMessageDelayed(1,10);
                }
            }else if(msg.what ==1){
                if(limit_time>0){
                    limit_time = (float)(limit_time-0.01);
                    String time = String.format("%.2f",limit_time);
                    timer.setText(time);
                    handler.sendEmptyMessageDelayed(1,10);
                    if(cnt == 12){
                        removeMessages(1);
                        removeMessages(2);
                        String score = (cnt*10)+"";
                        end_message(score);
                        game_reset();
                    }
                }else{
                    removeMessages(1);
                    removeMessages(2);
                    // 여기에 메세지를 띄워줌
                    String score = (cnt*10)+"";
                    end_message(score);
                    game_reset();
                }
            }else if(msg.what ==2){
                Log.d("mood","handler-> \n"+temp[0]+"/"+temp[1]+"\n"
                        +"ai: "+ai+" aj: "+aj+"\n"+"bi: "+bi + " bj: "+bj);
                arr[ai][aj].setText("");
                arr[bi][bj].setText("");
                ai = aj = bi = bj =-1;
            }
        }
    };

    int [] temp = new int[2];
    int ai,aj,bi,bj;
    boolean turn = true;
    int check = -1;
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.game_start){
            // 핸들러 이용, 전체 보여주고 10초 뒤에 다시 꺼버림
            handler.sendEmptyMessageDelayed(0,10);
            insert_num(); // 숫자 섞기 및, 버튼에 숫자 넣기 이건 게임 시작하면 보여줄것.
        }else if(v.getId() == R.id.game_quite){
            finish();
        }else if(v.getId() == R.id.game_reset){
            game_reset();
        }

        if(check == 1){
            for(int i=0; i<arr.length; i++){
                for(int j=0; j<arr[i].length; j++){
                    if(v.getId() == arr[i][j].getId()){
                        if(turn == true){
                            int k1 = (i*6)+j;
                            arr[i][j].setText(num[k1]+"");
                            temp[0] = num[k1];
                            ai = i; aj = j;
                            turn = false;
                        }else{
                            int k2 = (i*6) +j;
                            arr[i][j].setText(num[k2]+"");
                            temp[1] = num[k2];
                            bi = i; bj = j;
                            turn = true;
                        }
                        Log.d("mood","ai: "+ai+" aj: "+aj+"\n"+"bi: "+bi + " bj: "+bj);
                    }
                }
            }
            if(turn == true){
                if(temp[0] == temp[1]){
                    cnt++;
                    Log.d("mood","cnt: "+cnt);
                    temp[0] = 0;
                    temp[1] = 0;
                    arr[ai][aj].setEnabled(false);
                    arr[bi][bj].setEnabled(false);
                    ai = aj = bi = bj = -1;
                }else{
                    handler.sendEmptyMessageDelayed(2,500);
                    // 다시 사라지는 시간이 0.5초라 너무 빨리 클릭을 눌러버리면 배열 순서가 꼬여서
                    // 에러 발생
                }
            }
        }
    }
}
