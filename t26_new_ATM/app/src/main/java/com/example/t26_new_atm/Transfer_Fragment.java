package com.example.t26_new_atm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import Helper.AppHelper;

public class Transfer_Fragment extends Fragment implements View.OnClickListener {
    UserActivity activity;
    Button hundred,ten,fifth,one,all,acc_ck,pw_ck;
    EditText money,acc_num,pw;
    int total = 0;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (UserActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.transfer_fragment,container,false);
        hundred = v.findViewById(R.id.transfer_100);
        ten = v.findViewById(R.id.transfer_10);
        fifth = v.findViewById(R.id.transfer_5);
        one = v.findViewById(R.id.transfer_1);
        all = v.findViewById(R.id.transfer_all);
        acc_ck = v.findViewById(R.id.transfer_acc_ck);
        pw_ck = v.findViewById(R.id.transfer_pw_ck);

        hundred.setOnClickListener(this);
        ten.setOnClickListener(this);
        fifth.setOnClickListener(this);
        one.setOnClickListener(this);
        all.setOnClickListener(this);
        acc_ck.setOnClickListener(this);
        pw_ck.setOnClickListener(this);

        money = v.findViewById(R.id.transfer_money_et);
        acc_num = v.findViewById(R.id.transfer_acc_et);
        pw = v.findViewById(R.id.transfer_pw_et);

        return v;
    }

    private void checkTotal(){
        if(total >= activity.getTotal()){
            total = activity.getTotal();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.transfer_100){
            total +=100;
        }else if(v.getId() == R.id.transfer_10){
            total +=10;
        }else if(v.getId() == R.id.transfer_5){
            total += 5;
        }else if(v.getId() == R.id.transfer_1){
            total += 1;
        }else if(v.getId() == R.id.transfer_all){
            total = activity.getTotal();
        }
        if(total>activity.getTotal()){
            Toast.makeText(getContext(),"한도 초과입니다.",Toast.LENGTH_LONG).show();
        }
        checkTotal();
        money.setText(total+"");

        if(v.getId() == R.id.transfer_acc_ck){
            check_acc();
        }else if(v.getId() == R.id.transfer_pw_ck){

        }


    }

    public void check_acc(){
        String url = "hoozi.woobi.co.kr/ATM/"
    }
}
