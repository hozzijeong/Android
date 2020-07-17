package com.example.t25_fragmentviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    List_Fragment list_fragment;
    Viewer_Fragment viewer_fragment;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        list_fragment = (List_Fragment) fragmentManager.findFragmentById(R.id.list_fragment);
        viewer_fragment = (Viewer_Fragment) fragmentManager.findFragmentById(R.id.viewer_fragment);

    }

    public void setColor(int idx){
        if(idx == 0){
            viewer_fragment.tv.setBackgroundColor(Color.BLACK);
        }else if( idx ==1){
            viewer_fragment.tv.setBackgroundColor(Color.YELLOW);
        }else if( idx ==2){
            viewer_fragment.tv.setBackgroundColor(Color.BLUE);
        }else if( idx ==3){
            viewer_fragment.tv.setBackgroundColor(Color.RED);
        }else if( idx ==4){
            viewer_fragment.tv.setBackgroundColor(Color.GREEN);

        }
    }
}
