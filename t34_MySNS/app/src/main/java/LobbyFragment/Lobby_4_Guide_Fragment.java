package LobbyFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.t34_mysns.LobbyActivity;
import com.example.t34_mysns.R;

import AppHelper.Helper_Fragment;

public class Lobby_4_Guide_Fragment extends Helper_Fragment {
    private TextView guide_tv;
    public Button guide_btn;
    public String text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lobby_4_guide_fragment,container,false);
        guide_tv = v.findViewById(R.id.guide_tv);
        guide_btn = v.findViewById(R.id.guide_btn);
        guide_tv.setText(text);
        //설명을 다 읽고 다음을 누르면, read_guide는 true로 변경된다

        return v;
    }

}
