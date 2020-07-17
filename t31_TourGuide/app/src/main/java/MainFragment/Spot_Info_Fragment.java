package MainFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.t31_tourguide.MainActivity;
import com.example.t31_tourguide.R;

public class Spot_Info_Fragment extends Fragment {
    MainActivity activity;
    ImageView spot_image;
    TextView spot_explain,spot_title;
    int city_idx,spot_idx;
    ImageButton quite;
    Button map;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
        city_idx = activity.pos;
        spot_idx = activity.spot_idx;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.spot_info_fragment,container,false);
        spot_explain = v.findViewById(R.id.spot_explain);
        spot_image = v.findViewById(R.id.spot_image);
        spot_title = v.findViewById(R.id.spot_title);
        quite = v.findViewById(R.id.spot_quite);
        map = v .findViewById(R.id.spot_map);

        spot_title.setText(activity.infos.get(city_idx).spot_infos.get(spot_idx).spot_name);
        spot_explain.setText(activity.infos.get(city_idx).spot_infos.get(spot_idx).spot_explain);
        Glide.with(this).load(activity.infos.get(city_idx).spot_infos.get(spot_idx).url).into(spot_image);

        quite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(activity.pos,activity.turn);
                activity.spot_idx = -1;
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showMap(spot_idx);
            }
        });

        return v;
    }
}
