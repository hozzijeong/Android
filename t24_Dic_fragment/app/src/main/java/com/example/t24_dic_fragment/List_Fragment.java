package com.example.t24_dic_fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

import java.util.HashMap;
import java.util.Map;

import Helper.AppHelper;
import Helper.Myadapter;
import Helper.Voca;

public class List_Fragment extends Fragment implements Response.ErrorListener, AdapterView.OnItemClickListener {
    MainActivity activity;
    Myadapter myadapter;
    EditText eng,kor;
    Button insert,game,f5,quite;
    ListView wordList;
    boolean turn = false;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list,container,false);
        if(activity.user_idx == -1){
            activity.getFragment(5);
        }

        eng = v.findViewById(R.id.word_eng_et);
        kor = v.findViewById(R.id.word_kor_et);
        insert = v.findViewById(R.id.word_insert);
        game = v.findViewById(R.id.word_game);
        wordList = v.findViewById(R.id.word_List);
        f5 = v.findViewById(R.id.word_refresh);
        quite = v.findViewById(R.id.word_quite);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(turn == false){
                    addWord();
                }else{
                    modifyWord(pos);
                }
            }
        });
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(1);
            }
        });
        f5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWord();
            }
        });
        quite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(4);
                activity.voca.clear();
                activity.user_idx = -1;
            }
        });
        getWord();
        return v;
    }

    private void setAdapter(){
        myadapter = new Myadapter((Activity) getContext(),activity.voca);
        wordList.setOnItemClickListener(this);
        wordList.setAdapter(myadapter);
    }


    Response.Listener<String> getListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                Log.d("mood",response);
                String result = object.getString("result");
                Log.d("mood",result);
                if(result.equalsIgnoreCase("ok")){
                    JSONArray list = object.getJSONArray("list");
                    Log.d("mood",list.length()+"");
                    activity.voca.clear();
                    for(int i=0; i<list.length(); i++){
                        JSONObject temp = list.getJSONObject(i);
                        activity.voca.add(new Voca(Integer.parseInt(temp.getString("idx")),temp.getString("eng"),temp.getString("kor")));
                        Log.d("mood",activity.voca.get(i).idx+"/"+activity.voca.get(i).eng+"/"+activity.voca.get(i).kor);
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
        String url = "http://hozzi.woobi.co.kr/word_list/list.php";
        AppHelper.requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                getListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_idx",activity.user_idx+"");
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
        activity.voca.add(new Voca(Eng,Kor));
        insertWord();
    }

    Response.Listener<String> modifyListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                String result = object.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    turn = false;
                    setWord();
                    myadapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void modifyWord(final int idx){
        final String Eng = eng.getText().toString().trim();
        final String Kor = kor.getText().toString().trim();
        activity.voca.get(idx).eng = Eng;
        activity.voca.get(idx).kor = Kor;

        String url = "http://hozzi.woobi.co.kr/word_list/update.php";
        AppHelper.requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                modifyListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("eng",Eng);
                params.put("kor",Kor);
                params.put("idx",activity.voca.get(idx).idx+"");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);
    }

    Response.Listener<String> insertListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                String result = object.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    Toast.makeText(getContext(),"저장 완료",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"저장 실패",Toast.LENGTH_LONG).show();
                }
                myadapter.notifyDataSetChanged();
                setWord();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void insertWord(){
        String url = "http://hozzi.woobi.co.kr/word_list/insert.php";
        AppHelper.requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                insertListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_idx",activity.user_idx+"");
                params.put("eng",eng.getText().toString().trim());
                params.put("kor",kor.getText().toString().trim());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);
    }

    Response.Listener<String> removeListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                String temp = object.getString("result");
                if(temp.equalsIgnoreCase("ok")){
                    activity.voca.remove(pos);
                    myadapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void removeWord(final int idx){
        String url = "http://hozzi.woobi.co.kr/word_list/delete.php";
        AppHelper.requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                removeListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idx",activity.voca.get(idx).idx+"");
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    int pos;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        this.pos = position;
        AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
        ab.setTitle("Message").setMessage("수정/삭제 하겠습니까?");

        ab.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                turn = true;
                eng.setText(activity.voca.get(position).eng);
                kor.setText(activity.voca.get(position).kor);
            }
        });

        ab.setNeutralButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeWord(position);
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

}
