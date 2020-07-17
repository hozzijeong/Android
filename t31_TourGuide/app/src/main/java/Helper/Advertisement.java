package Helper;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import MainFragment.Ad_Fragment;

public class Advertisement extends FragmentStatePagerAdapter {

    public Advertisement(@NonNull FragmentManager fm) {
        super(fm);

    }

    String[] colors = {
            "#ff0000",
            "#ffff00",
            "#ff00ff",
            "#00f000",
            "#fff000",
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Ad_Fragment ad = new Ad_Fragment();
        ad.color = colors[position];
        return ad;
    }

    @Override
    public int getCount() {
        return 5;
    }



}
