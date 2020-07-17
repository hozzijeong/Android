package com.example.t24_dic_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GLobby_Fragment extends Fragment {
    MainActivity activity;
    Button hang,game,quite;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.globby,container,false);

        hang = v.findViewById(R.id.game_lobby_hang_man);
        game = v.findViewById(R.id.game_lobby_word_game);
        quite = v.findViewById(R.id.game_lobby_quite);

        hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(2);
            }
        });
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(6);
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
