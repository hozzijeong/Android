package com.example.t26_new_atm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class User_Fragment extends Fragment {
    UserActivity activity;
    Button  check,transfer,leave,quite;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (UserActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_lobby,container,false);

        check = v.findViewById(R.id.user_check_acc);
        transfer = v.findViewById(R.id.user_transfer);
        leave = v.findViewById(R.id.user_leave);
        quite = v.findViewById(R.id.user_quite);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(0);
            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(1);
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(2);
            }
        });

        quite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(4);
            }
        });

        return v;
    }
}
