package MainFragment;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.t31_tourguide.MainActivity;
import com.example.t31_tourguide.R;

import Helper.City_Adapter;

public class Tour_Spot_Preview_Fragment extends Fragment {
    MainActivity activity;
    ViewPager viewPager;
    City_Adapter adapter;
    TextView explain,idx;
    ListView tour_spot_lv;
    LinearLayout city_layout;
    ImageView imageView;
    public String page,url;
    int city_idx;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
        city_idx = activity.pos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tour_spot_fragment,container,false);
        explain = v.findViewById(R.id.city_explain);
        idx = v.findViewById(R.id.city_index);
        city_layout = v.findViewById(R.id.city_layout);
        tour_spot_lv = v.findViewById(R.id.city_spot_list);

//        imageView = v.findViewById(R.id.city_image);
//        Glide.with(this).load(url).into(imageView);

        city_layout.setVisibility(View.VISIBLE);
        tour_spot_lv.setVisibility(View.INVISIBLE);

        viewPager = v.findViewById(R.id.city_viewpager);
        adapter = new City_Adapter(activity.getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        explain.setText("activity.infos.get(city_idx).explain");
        idx.setText(page);


        return v;
    }
}
