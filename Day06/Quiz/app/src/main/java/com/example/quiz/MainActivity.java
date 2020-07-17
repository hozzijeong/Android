package com.example.quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView questionTv;
    TextView chTv1;
    TextView chTv2;
    TextView chTv3;
    TextView chTv4;
    TextView scoreTv;
    TextView indexTv;

    String[] questionArr = new String[3];
    String[] ch1Arr = new String[3];
    String[] ch2Arr = new String[3];
    String[] ch3Arr = new String[3];
    String[] ch4Arr = new String[3];
    String[] ansArr = new String[3];

    int idx = 0;
    int score = 0;
    String answer = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionTv = findViewById(R.id.questionTv);
        chTv1 = findViewById(R.id.chTv1);
        chTv2 = findViewById(R.id.chTv2);
        chTv3 = findViewById(R.id.chTv3);
        chTv4 = findViewById(R.id.chTv4);
        scoreTv = findViewById(R.id.scoreTv);
        indexTv = findViewById(R.id.indexTv);

        init();
        show();
        chTv1.setOnClickListener(this);
        chTv2.setOnClickListener(this);
        chTv3.setOnClickListener(this);
        chTv4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String myAnswer = "";
        switch (v.getId()) {
            case R.id.chTv1:
                myAnswer = ch1Arr[idx];
                break;
            case R.id.chTv2:
                myAnswer = ch2Arr[idx];
                break;
            case R.id.chTv3:
                myAnswer = ch3Arr[idx];
                break;
            case R.id.chTv4:
                myAnswer = ch4Arr[idx];
                break;
        }
        if (myAnswer.equals(answer)) {
            //정답
            score += 10;
            scoreTv.setText(score + "점");
        }
        idx++;
        indexTv.setText(idx + "/" + questionArr.length);
        if (idx == questionArr.length) {
            showFinishDialog();
        } else {
            show();
        }
    }

    private void showFinishDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage("모든 퀴즈를 풀었습니다" +
                "다시 시작하시겠슴?");
        ab.setPositiveButton("재시작", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idx = 0;
                score = 0;
                scoreTv.setText(score + "점");
                indexTv.setText((idx + 1) + "/" + questionArr.length);
                show();
            }
        });
        ab.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        ab.setCancelable(false);
        ab.show();
    }


    private void show() {
        String question = questionArr[idx];
        String ch1 = ch1Arr[idx];
        String ch2 = ch2Arr[idx];
        String ch3 = ch3Arr[idx];
        String ch4 = ch4Arr[idx];
        questionTv.setText(question);
        chTv1.setText(ch1);
        chTv2.setText(ch2);
        chTv3.setText(ch3);
        chTv4.setText(ch4);
        answer = ansArr[idx];
    }

    private void init() {
        String response = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("result", "OK");

            JSONArray quesArr = new JSONArray();
            JSONObject q1 = new JSONObject();
            q1.put("question", "한국의 수도는?");
            q1.put("ch1", "서울");
            q1.put("ch2", "시카고");
            q1.put("ch3", "워싱턴");
            q1.put("ch4", "부산");
            q1.put("ans", "1");
            quesArr.put(q1);

            JSONObject q2 = new JSONObject();
            q2.put("question", "1+1=?");
            q2.put("ch1", "10");
            q2.put("ch2", "20");
            q2.put("ch3", "2");
            q2.put("ch4", "7");
            q2.put("ans", "3");
            quesArr.put(q2);

            JSONObject q3 = new JSONObject();
            q3.put("question", "what is your name?");
            q3.put("ch1", "Tom");
            q3.put("ch2", "Jay");
            q3.put("ch3", "Julie");
            q3.put("ch4", "개똥이");
            q3.put("ans", "4");
            quesArr.put(q3);

            jsonObject.put("list", quesArr);
            response = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject totalObj = new JSONObject(response);
            String result = totalObj.getString("result");
            if (result.equals("OK")) {
                JSONArray list = totalObj.getJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject temp = list.getJSONObject(i);
                    questionArr[i] = temp.getString("question");
                    ch1Arr[i] = temp.getString("ch1");
                    ch2Arr[i] = temp.getString("ch2");
                    ch3Arr[i] = temp.getString("ch3");
                    ch4Arr[i] = temp.getString("ch4");
                    ansArr[i] = temp.getString("ans");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
