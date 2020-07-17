package MainFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.t31_tourguide.MainActivity;
import com.example.t31_tourguide.R;

import Helper.AppHelper;
import Helper.City_Adapter;

public class Specific_City_Info_Fragment extends Fragment {
    MainActivity activity;
    int city_idx;
    TextView continent,city,title;
    Button list,preview;
    ImageButton star,quite;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
            activity = (MainActivity) getActivity();
            if (activity != null) {
                city_idx = activity.pos;
//                AppHelper.getLog("city_idx: "+city_idx); // 몇 번째 도시인가를 나타내주는 idx
                if (city_idx == -1) {
                    activity.replaceFragment(city_idx);
                }
            }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.specific_city_info_fragment,container,false);
        title = v.findViewById(R.id.city_title);
        continent = v.findViewById(R.id.city_continent);
        city = v.findViewById(R.id.city_name);
        list = v.findViewById(R.id.city_tour_list);
        preview =v.findViewById(R.id.city_tour_preview);
        quite = v.findViewById(R.id.city_quite);

        star = v.findViewById(R.id.city_star);

        title.setText(MainActivity.infos.get(city_idx).city);
        continent.setText("["+ MainActivity.infos.get(city_idx).continent+"]\t");
        city.setText(MainActivity.infos.get(city_idx).city);

        //idx 값에 viewpager adapter 에서 position 값 받아서 같이 넣어 줄 것.
        AppHelper.getLog("star: "+MainActivity.infos.get(city_idx).star);
        if(MainActivity.infos.get(city_idx).star){
            star.setImageResource(R.drawable.top_bookmark_on);
        }else{
            star.setImageResource(R.drawable.top_bookmark_off);
        }

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.turn = false;
                activity.addFragment(city_idx, false);
            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.turn = true;
                activity.addFragment(city_idx,true);
            }
        });

        quite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.turn = true;
                activity.replaceFragment(-1);
            }
        });

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.infos.get(city_idx).star){
                    star.setImageResource(R.drawable.top_bookmark_off);
                    MainActivity.infos.get(city_idx).star = false;
                }else{
                    star.setImageResource(R.drawable.top_bookmark_on);
                    MainActivity.infos.get(city_idx).star = true;
                }
            }
        });

        return v;
    }
}
