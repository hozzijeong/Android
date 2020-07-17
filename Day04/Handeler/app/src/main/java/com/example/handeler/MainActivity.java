package com.example.handeler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv,tv_02;
    //Thread 동시에 처리함.
    //    MyThread th;
    Button btn;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        btn = findViewById(R.id.ok);
        img = findViewById(R.id.img);
        tv_02 = findViewById(R.id.tv_02);

        btn.setOnClickListener(this);
//        th = new MyThread();
//        th.run(); 이렇게 하면 순차적으로 실행이됨
//        th.start();
//        for(int i=0; i<30; i++){
//            Log.d("check","main"+i);
//        }
        handler.sendEmptyMessageDelayed(0, 1000);

    }

    // 클릭하면 초기화
    int i;
    // handler는 android.os에 있는것.
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            // what은 핸들러에서 약간 key값? 으로 볼 수 있음.

            if(msg.what == 1){
                turn = true;
                img.setImageResource(R.drawable.penguin);
                Log.d("turn","handler "+turn);
            }else{
                if(i<10){
                    i++;
                    tv.setText("시간"+i);
                    handler.sendEmptyMessageDelayed(0, 1000);// 앱이 종료되도 값이 계속해서 돌아감.
                }else{
                    tv.setText("끝났습니다.");
                    img.setImageResource(R.drawable.ic_launcher_background);
                }
            }
        }
    };

    boolean turn = true;
    @Override
    public void onClick(View v) {
        Log.d("turn","click "+turn);
        if(v.getId() == R.id.ok) {
            if(turn == true){ // turn이 true이면 펭귄 사진을 나타낸다는 의미이고, turn이 false이면 펭수 그림이 나오도록 설정.
                img.setImageResource(R.drawable.pengsu); // 이미지를 펭수로 바꾸고
                handler.sendEmptyMessageDelayed(1, 5000); // delay초 뒤에 1이라는 아이디를 만든 무엇인가를 하나 새로 만든다는 의미 .
                turn = false; //turn을 false 로 바꾼다.
            }else{
                handler.removeMessages(1); // turn이 false 일때, 클릭을 새로 하면 기존에 있던 1의 값을 지워버리고 새로운 1의 값을 설정(초기화)
                // removeMessage를 사용할 때, handler에 해당하는 what 값이 없어도 실행이 된다.
                handler.sendEmptyMessageDelayed(1,5000);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0); // 0번을 가진 값을 모두 종료해 달라 라는 의미
    }
}

// 스레드와 핸들러는 속도 차이가 엄청나기 때문에, 일을 돌리는 것은 스레드를 이용(압축, 바이러스 검사는 스레드로 체크)
// 하고 인간의 눈이 인식할 수 있는 정도의 변화를 핸들러로 표시.

//    class MyThread extends Thread{
//        // 오리지널 스레드가 아닌 커스텀 스레드에서 UI터치(UI의 변형)를 허용하지 않음. 따라서 핸들러를 통해 UI를 변경함.
//        // 메인 스레드가 끝나기 전에 이전 스레드가 멈춰야함.
//        @Override
//        public void run() {
//            super.run();
//                try{
//                    Thread.sleep(5000);
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//            handler.sendEmptyMessage(0); // int에 들어가는 수는 아무거나 상관 없음.
//        }
//    }

// onClick?
//}
