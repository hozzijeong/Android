package com.example.t23_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment3 extends Fragment {
    MainActivity activity;
    int num = 3;

    @Override
    public void onAttach(@NonNull Context context) {
        activity = (MainActivity) getActivity();
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment3,container,false);

        Button btn = v.findViewById(R.id.fr3_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFra(num);
            }
        });

        return v;
    }
}
