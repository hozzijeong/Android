package com.example.t24_dic_fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Random;

import Helper.Voca;

public class Hangman_Fragment extends Fragment {
    MainActivity activity;
    Boolean[] check_box;
    TextView quiz;
    EditText input;
    Button insert,quite;
    int quiz_num = 0;
    String answer = "";
    String cor_answer = "";
    int life_cnt = 7;

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
        View v = inflater.inflate(R.layout.hangman,container,false);

        quiz = v.findViewById(R.id.hang_quiz);
        input = v.findViewById(R.id.hang_input);
        insert = v.findViewById(R.id.hang_insert_btn);
        quite = v.findViewById(R.id.hang_quite);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_answer();
            }
        });
        quite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(1);
            }
        });

        shuffle();
        setQuiz();
        return v;
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
    private void setQuiz(){
        if(quiz_num > 0){
            check_box = null;
        }
        int quiz_size = activity.voca.get(quiz_num).eng.length();
        cor_answer = activity.voca.get(quiz_num).eng;
        Log.d("mood",cor_answer);
        check_box = new Boolean[quiz_size];
        for(int i=0; i<quiz_size; i++){
            check_box[i] = false;
            answer += "* ";
        }
        quiz.setText(answer);
    }

    private void showQuiz(){
        answer = "";
        for(int i=0; i<activity.voca.size();i++){
            if(check_box[i]){
                answer += cor_answer.charAt(i);
            }else{
                answer += "* ";
            }
        }
        quiz.setText(answer);
        input.setText("");
    }

    private void check_answer(){
        boolean turn = false;
        for(int i=0; i<cor_answer.length(); i++){
            Toast.makeText(getContext(),input.getText().toString() +" : "+cor_answer.charAt(i),Toast.LENGTH_LONG).show();
            if(input.getText().toString().equals(cor_answer.charAt(i))){
                check_box[i] = true;
                turn = true;
            }
        }
        showQuiz();

//        if(!turn){
//            life_cnt --;
//            Toast.makeText(getContext(),"오답입니다...\n"+ life_cnt+"번 남았습니다",Toast.LENGTH_LONG).show();
//        }


        if(answer.equals(cor_answer)){
            Toast.makeText(getContext(),"정답입니다!",Toast.LENGTH_LONG).show();
            quiz_num ++;
            if(quiz_num < activity.voca.size()-1){
                setQuiz();
            }else{
                Toast.makeText(getContext(),"문제를 다풀었습니다",Toast.LENGTH_LONG).show();
            }
        }
        if(life_cnt == 0){
            Toast.makeText(getContext(),"You Dead...\n " +
                    "the answer is "+cor_answer,Toast.LENGTH_LONG).show();
        }
    }

}
