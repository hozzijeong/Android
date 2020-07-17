package com.example.t25_fragmentviewer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class List_Fragment extends Fragment {

    MainActivity activity;
    ListView lv;
    ArrayAdapter adapter;
    ArrayList<String> array = new ArrayList<>();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.listfragment,container,false);

        array.add("검정");
        array.add("노랑");
        array.add("파랑");
        array.add("빨강");
        array.add("초록");


        lv = v.findViewById(R.id.list);
        adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,array);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity.setColor(position);
            }
        });
        return v;
    }

}
