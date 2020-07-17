package com.example.t24_dic_fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

import Helper.Voca;

public class WGame_Fragment extends Fragment {
    MainActivity activity;
    Button play,input,quite;
    TextView quiz,timer,process;
    EditText answer;
    ProgressBar pro;

    boolean turn = false;
    int cnt = 0;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wgame,container,false);

        play = v.findViewById(R.id.game_play);
        input = v.findViewById(R.id.game_input);
        quite = v.findViewById(R.id.game_quite);
        quiz = v.findViewById(R.id.game_quiz);
        timer = v.findViewById(R.id.game_timer);
        process = v.findViewById(R.id.game_process);
        pro = v.findViewById(R.id.game_process_bar);
        answer = v.findViewById(R.id.game_answer);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!turn){
                    input.setEnabled(true);
                    setting();
                }
            }
        });
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity.voca.get(cnt).kor.equals(answer.getText().toString())){
                    Toast.makeText(getContext(),"정답",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"오답",Toast.LENGTH_LONG).show();
                }
                answer.setText("");
                cnt++;
                showQuiz();

            }
        });
        quite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(1);
            }
        });

        input.setEnabled(false);
        play.setEnabled(true);

        return v;
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
        pro.setMax(activity.voca.size());
        pro.setProgress(cnt);
    }

    private void showQuiz(){
        if(cnt!= activity.voca.size()){
            quiz.setText(activity.voca.get(cnt).eng);
            process.setText(cnt+"/"+activity.voca.size());
        }else{
            Toast.makeText(getContext(),"문제를 다 풀었습니다.",Toast.LENGTH_LONG).show();
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
        process.setText(cnt+"/"+activity.voca.size());
        quiz.setText("Quiz");
        timer.setText("00:00.00");
        pro.setProgress(cnt);
    }


    private void shuffle(){
        Random ran = new Random();
        for(int i=0; i<500; i++){
            int r = ran.nextInt(activity.voca.size());
            Voca temp =  activity.voca.get(0);
            activity.voca.set(0,activity.voca.get(r));
            activity.voca.set(r,temp);
        }
    }
}
