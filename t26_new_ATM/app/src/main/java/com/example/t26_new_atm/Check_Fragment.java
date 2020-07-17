package com.example.t26_new_atm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import Helper.AppHelper;
import Helper.MyAdapter;


public class Check_Fragment extends Fragment {
    UserActivity activity;
    MyAdapter adapter;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (UserActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.check_fragment,container,false);

        v.findViewById(R.id.acc_quite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(3);
            }
        });


        adapter = new MyAdapter((Activity) getContext(),activity.acc_infos);
        adapter.notifyDataSetChanged();

        return v;
    }
}
