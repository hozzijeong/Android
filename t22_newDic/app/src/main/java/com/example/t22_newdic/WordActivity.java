package com.example.t22_newdic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Helper.AppHelper;
import Helper.Myadapter;
import Helper.Voca;

public class WordActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener,
        Response.ErrorListener {
    static String token;
    ArrayList<Voca> voca = new ArrayList<>();
    // 서버에 단어를 저장하는데 굳이 DB를 사용해야함? -> 안해도 됨
    // 단어 리스트에 있는 idx값은 어디서 얻은 거임? -> 서버에서 저장된 table의 idx값임.
    EditText eng,kor;
    Button insert,game;
    ListView wordList;
    Myadapter myadapter = null;
    boolean turn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        eng = findViewById(R.id.word_eng_et);
        kor = findViewById(R.id.word_kor_et);
        insert = findViewById(R.id.word_insert);
        game = findViewById(R.id.word_game);
        wordList = findViewById(R.id.word_List);

        insert.setOnClickListener(this);
        game. setOnClickListener(this);

        getWord();
    }

    private void setAdapter(){
        myadapter = new Myadapter(this,voca);
        wordList.setOnItemClickListener(this);
        wordList.setAdapter(myadapter);
    }

    Response.Listener<String> getListener = new Response.Listener<String>() {
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
                    setAdapter();
                    myadapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void getWord(){
        String url = "http://";
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                getListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("token",token);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);

    }

    private void setWord(){
        eng.setText("");
        kor.setText("");
    }
    private void addWord(){
        String Eng = eng.getText().toString().trim();
        String Kor = kor.getText().toString().trim();
        voca.add(new Voca(Eng,Kor));
        insertWord();
        myadapter.notifyDataSetChanged();
    }
    private void modifyWord(int idx){
        String Eng = eng.getText().toString().trim();
        String Kor = kor.getText().toString().trim();
        voca.get(idx).eng = Eng;
        voca.get(idx).kor = Kor;
        turn = false;
        setWord();
        myadapter.notifyDataSetChanged();
    }

    Response.Listener<String> insertListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                String result = object.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    Toast.makeText(getApplicationContext(),"저장 완료",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"저장 실패",Toast.LENGTH_LONG).show();
                }
                setWord();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void insertWord(){
        String url = "http://";
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
          insertListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("token",token);
                params.put("eng",eng.getText().toString().trim());
                params.put("kor",kor.getText().toString().trim());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.word_game){
            Intent intent = new Intent(this,com.example.t22_newdic.GameActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.word_insert){
            if(turn == false){
                addWord();
            }else{
                modifyWord(pos);
            }
        }
    }
    int pos;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        this.pos = position;
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Message").setMessage("수정/삭제 하겠습니까?");

        ab.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                turn = true;
                eng.setText(voca.get(position).eng);
                kor.setText(voca.get(position).kor);
            }
        });

        ab.setNeutralButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                voca.remove(position);
                myadapter.notifyDataSetChanged();
            }
        });
        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        ab.setCancelable(false);
        ab.show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
