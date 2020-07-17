package LobbyFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.t31_tourguide.LobbyActivity;
import com.example.t31_tourguide.MainActivity;
import com.example.t31_tourguide.R;

import Helper.AppHelper;

public class Personal_info_Fragment extends Fragment {

    LobbyActivity activity;
    CheckBox first;
    CheckBox second;

    SharedPreferences sp;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (LobbyActivity) getActivity();
        sp = activity.getSharedPreferences("user_agree",Context.MODE_PRIVATE);

    }


//    private void get_Check(){
//        first.setChecked(sp.getBoolean("first",false));
//        second.setChecked(sp.getBoolean("second",false));
//        AppHelper.getLog("first: "+first.isChecked());
//        AppHelper.getLog("second: "+second.isChecked());
//    }


    private void save_Check(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("first",first.isChecked());
        editor.putBoolean("second",second.isChecked());
        AppHelper.getLog("result_f: "+first.isChecked());
        AppHelper.getLog("result_s: "+second.isChecked());
        editor.commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.persoal_info_fragment,container,false);
        first = v.findViewById(R.id.first_box);
        second = v.findViewById(R.id.second_box);
//        get_Check();
//        if(first.isChecked() && second.isChecked()){
//            Intent intent = new Intent(getActivity(), MainActivity.class);
//            startActivity(intent);
//        }
        activity.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(first.isChecked() && second.isChecked()){
                    save_Check();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                    ab.setTitle("알림").setMessage("동의를 모두 체크하셔야\n서비스 이용이 가능합니다.");
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
