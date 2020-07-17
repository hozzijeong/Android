package com.example.t08_newdic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class RankActivity extends AppCompatActivity {
    ArrayList<Rank> rankdata = new ArrayList<>();
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        Intent intent = getIntent();
        rankdata = (ArrayList<Rank>) intent.getSerializableExtra("rankdata");
        rankdata.get(0).id = "";
        tv = findViewById(R.id.rank_tv);
        tv.setText(rankdata.get(0).id);

    }
}
