package com.example.json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String response = "";
        try {
            JSONArray jArr = new JSONArray();

            JSONObject obj = new JSONObject();
            obj.put("question","");
            obj.put("kor","개");
            jArr.put(obj);

            JSONObject obj2 = new JSONObject();
            obj2.put("eng","cat");
            obj2.put("kor","고양아");
            jArr.put(obj2);

            Log.d("heu","obj: "+jArr.toString());
            response =jArr.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //서버에서 데이터를 받은후
        try {
            JSONArray tempJ = new JSONArray(response);
            for (int i = 0; i < tempJ.length(); i++) {
                JSONObject temp = tempJ.getJSONObject(i);
                Log.d("heu" , "eng: "+temp.getString("eng"));
                Log.d("heu" , "kor: "+temp.getString("kor"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
