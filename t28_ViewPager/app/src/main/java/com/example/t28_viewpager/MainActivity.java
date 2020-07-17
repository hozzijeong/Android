package com.example.t28_viewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Fragment1 fragment1;
    ViewAdapter adapter;
    ViewPager viewPager;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        adapter = new ViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0); // position값을 0으로 만듦
//                viewPager.setCurrentItem(0,false);// position값을 부드럽게 0으로 만듦
//                viewPager.setPageMargin(200); // viewPage간의 간격
            }
        });
    }

    class ViewAdapter extends FragmentStatePagerAdapter{
        public ViewAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            fragment1 = new Fragment1();

            fragment1.str = (position+1)+"번 ViewPager";

            return fragment1;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
