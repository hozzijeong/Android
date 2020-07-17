package com.example.t15_1to50;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    Button arr[][] = new Button[5][5];
    Button start,quite,reset;
    TextView timer;
    int front_num[] = new int[25];
    String time;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        start = findViewById(R.id.game_start);
        quite = findViewById(R.id.game_quite);
        timer = findViewById(R.id.game_timer);
        reset = findViewById(R.id.game_reset);

        start.setOnClickListener(this);
        quite.setOnClickListener(this);
        reset.setOnClickListener(this);
        /*
           1. 시작을 누르기 전에는 게임 숫자를 보여주지 않는다.
           2. 시작을 누른 뒤, 타이머와 함께 숫자가 나타난다.
           3. 숫자 n을 맞추면 +25를 한다.
           4. 같이 보조를 맞출 숫자 num이 존재한다.
           5. 데이터 저장은 랭킹을 저장하는 동시에 한다.
           6. While문 활용 잘하자... 잘.. 보자
         */
       set_Button(); // 버튼을 연결까지만 함.
    }

    public void load_db_data(){
        SQLiteDatabase db = openOrCreateDatabase("Rank.db",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT*FROM rank_list",null);
        c.moveToFirst();
        MainActivity.user_infos.clear();
        while (c.isAfterLast() == false){
            MainActivity.user_infos.add(
                    new user_info(c.getString(1),c.getString(2)));
            c.moveToNext();
        }
        c.close();
        db.close();
    }

    public void add_db(String name,String time){
        SQLiteDatabase db = openOrCreateDatabase("Rank.db",MODE_PRIVATE,null);
        db.execSQL("INSERT INTO rank_list(name,time) VALUES('"+name+"','"+time+"')");
        db.close();
    }


    public void set_Button(){
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[i].length; j++){
                arr[i][j] = findViewById(R.id.btn1_1+((i*5)+j));
                arr[i][j].setOnClickListener(this);
                Log.d("mood","arr["+i+"]["+j+"].getId(): "+arr[i][j].getId());
            }
        }
    }

    public void show_Button(){
        shuffle_num();
        int k=0;
        for(int i =0; i<arr.length; i++){
            for(int j=0; j<arr[i].length; j++){
                arr[i][j].setText(front_num[k]+"");
                k++;
            }
        }
    }

    void shuffle_num(){
        Random ran = new Random();
        for(int i=0; i<front_num.length;i++){
            front_num[i] = (i+1);
        }

        for(int i=0; i<500; i++){
            int r = ran.nextInt(front_num.length);
            int temp = front_num[0];
            front_num[0] = front_num[r] ;
            front_num[r] = temp;
        }
    }

    void Reset_game(){
        handler.removeMessages(0);
        for(int i=0; i<arr.length;i++){
            for(int j=0; j<arr[i].length; j++){
                arr[i][j].setText("");
            }
        }
        count = 1;
        time = "0";
        timer.setText("00:00.00");
    }

    float i;
    Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                i = (float) (i+0.01);
                time = String.format("%.2f",i);
                // double은 안되고 왜 float만 되는 걸까?
                timer.setText(time+"초");
                handler.sendEmptyMessageDelayed(0,10);
            }
        }
    };

    @Override
    public void onClick(View v) {
        if(count == 50){
            final String record = time;
            handler.removeMessages(0);
            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            ab.setTitle("게임 종료").setMessage("ID를 입력하세요");
            final EditText et = new EditText(getApplicationContext());
            ab.setView(et);
            ab.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    MainActivity.user_infos.add(new user_info(et.getText().toString(),record));

                    add_db(et.getText().toString(),record);
                    load_db_data();

                    Toast.makeText(getApplicationContext(),
                            et.getText().toString()+":"+record+"초",Toast.LENGTH_LONG).show();
                    Reset_game();
                }
            });
            ab.setCancelable(true);
            ab.show();
        }

        if (v.getId() == R.id.game_start) {
            show_Button();
            handler.sendEmptyMessageDelayed(0, 10);
        } else if (v.getId() == R.id.game_quite) {
            finish();
        }else if(v.getId() == R.id.game_reset){
            Reset_game();
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (v.getId() == arr[i][j].getId()) {
                    if (arr[i][j].getText().equals(count + "")) {
                        if(count<26){
                            arr[i][j].setText((count +25)+"");
                        }else if(count>25){
                            arr[i][j].setText("");
                            arr[i][j].setBackgroundColor(Color.parseColor("#FFFFFF"));
                        }
                        count++;
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }
}
