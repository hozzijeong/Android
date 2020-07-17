package com.example.t30_advertisementbanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class Advertisement extends Fragment {
    String url = "";
    ImageView iv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.advertisement,container,false);
        iv = v.findViewById(R.id.iv);
        Glide.with(this).load(url).into(iv);
        return v;
    }
}
