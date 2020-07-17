package com.example.t22_newdic;

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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Helper.AppHelper;
import Helper.Voca;

/*
    단어들의 세팅은 getWord() 가 끝나고 난 뒤 실행되야 하는 것들임.
    1. start버튼 활성화 -> start버튼을 누르고 나면 게임 실행
 */


public class GameActivity extends AppCompatActivity implements View.OnClickListener,Response.ErrorListener {
    ArrayList<Voca> voca = new ArrayList<>();
    Button play,input,quite;
    TextView quiz,timer,process;
    EditText answer;
    ProgressBar pro;

    boolean turn = false;
    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        play = findViewById(R.id.game_play);
        input = findViewById(R.id.game_input);
        quite = findViewById(R.id.game_quite);
        quiz = findViewById(R.id.game_quiz);
        timer = findViewById(R.id.game_timer);
        process = findViewById(R.id.game_process);
        pro = findViewById(R.id.game_process_bar);
        answer = findViewById(R.id.game_answer);

        play.setOnClickListener(this);
        input.setOnClickListener(this);
        quite.setOnClickListener(this);

        input.setEnabled(false);
        play.setEnabled(true);
        getWord();
    }

    Response.Listener<String> wordListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                String result = object.getString("result");
                Log.d("mood",result);
                if(result.equalsIgnoreCase("ok")){
                    JSONArray list = object.getJSONArray("list");
                    for(int i=0; i<list.length(); i++){
                        JSONObject temp = list.getJSONObject(i);
                        voca.add(new Voca(temp.getString("eng"),temp.getString("kor")));
                        Log.d("mood",voca.get(i).eng+"/"+voca.get(i).kor);
                    }
                    play.setEnabled(true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void getWord(){
        String url = "";
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                wordListener, this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("token",WordActivity.token);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);
    }

    float time = 0;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            time = (float) (time +0.1);
            String t = String.format("%.1f",time);
            timer.setText(t);
            handler.sendEmptyMessageDelayed(0,100);
        }
    };

    private void setting(){
        /*
            0. 문제 섞기(배열에 있는 단어들)
            1. 문제 제시
            2. 타이머 작동
         */
            shuffle();
            showQuiz();
            handler.sendEmptyMessageAtTime(0,100);
            turn = true;
            pro.setMax(voca.size());
            pro.setProgress(cnt);
        }

        private void showQuiz(){
            if(cnt!= voca.size()){
                quiz.setText(voca.get(cnt).eng);
                process.setText(cnt+"/"+voca.size());
            }else{
                Toast.makeText(this,"문제를 다 풀었습니다.",Toast.LENGTH_LONG).show();
                reset();
            }
        }

        private void reset(){
            shuffle();
            time = 0;
            handler.removeMessages(0);
            input.setEnabled(false);
            turn = false;
            cnt = 0;
            process.setText(cnt+"/"+voca.size());
            quiz.setText("Quiz");
            timer.setText("00:00.00");
            pro.setProgress(cnt);
        }


        private void shuffle(){
            Random ran = new Random();
            for(int i=0; i<500; i++){
                int r = ran.nextInt(voca.size());
                Voca temp =  voca.get(0);
                voca.set(0,voca.get(r));
                voca.set(r,temp);
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.game_play){
            Log.d("mood", "check: " + 1234);
            if(!turn){
                input.setEnabled(true);
                setting();
            }else{
            }
        }else if(v.getId() == R.id.game_input){
            if(voca.get(cnt).kor.equals(answer.getText().toString())){
                Toast.makeText(this,"정답",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"오답",Toast.LENGTH_LONG).show();
            }
                answer.setText("");
                cnt++;
                showQuiz();
        }else if(v.getId() == R.id.game_quite){
            finish();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
