package LobbyFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.t34_mysns.LobbyActivity;
import com.example.t34_mysns.R;

import AppHelper.Helper;
import AppHelper.Helper_Fragment;

public class Lobby_3_Agree_Fragment extends Helper_Fragment {
    public CheckBox check1,check2;
    public Button back,ok;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lobby_3_agree_fragment,container,false);
        check1 = v.findViewById(R.id.agree_check1);
        check2 = v.findViewById(R.id.agree_check2);
        back = v.findViewById(R.id.agree_back);
        ok = v.findViewById(R.id.agree_ok);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lobbyActivity.getFragment(0);

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1.isChecked() && check2.isChecked()) {
                    lobbyActivity.getFragment(1);
                } else {
                    AlertDialog.Builder ab = new AlertDialog.Builder(lobbyActivity);
                    ab.setTitle("메세지").setMessage("이용 약관 모두 동의 해주세요");
                    ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    ab.show();
                }
            }
        });

        return v;
    }


}
