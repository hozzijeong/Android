package MainFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.t34_mysns.MainActivity;
import com.example.t34_mysns.R;

import AppHelper.Helper_Fragment;
import AppHelper.Helper_Main_Fragment;
import AppHelper.Main_1_Feed_Adapter;

public class Main_5_Setting_Fragment extends Helper_Main_Fragment {

    Main_1_Feed_Adapter adapter;
    ListView feed_lv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_1_feed_fragment,container,false);
        return v;
    }
}
