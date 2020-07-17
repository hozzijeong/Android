package LobbyFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.t31_tourguide.LobbyActivity;
import com.example.t31_tourguide.R;

import Helper.AppHelper;

public class App_Guide_Fragment extends Fragment {
    public String str;
    LobbyActivity activity;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (LobbyActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.app_guide_fragment,container,false);
        TextView tv = v.findViewById(R.id.guide_tv);
        tv.setText(str);

        return v;
    }
}
