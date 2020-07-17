package MainFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.t31_tourguide.MainActivity;
import com.example.t31_tourguide.R;

import java.util.ArrayList;

import Helper.Place_Info;
import Helper.Spot_Adapter;

public class Tour_Spot_List_Fragment extends Fragment {
    MainActivity activity;
    ListView tour_spot_lv;
    LinearLayout city_layout;
    //여기서 클릭하는 position 값은 city_idx에 있는 spot_info의 position 값을 의미함
    ArrayList<Place_Info.spot_info> arrayList;
    Spot_Adapter adapter;
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
        city_layout = v.findViewById(R.id.city_layout);
        tour_spot_lv = v.findViewById(R.id.city_spot_list);

        city_layout.setVisibility(View.INVISIBLE);
        tour_spot_lv.setVisibility(View.VISIBLE);

        arrayList = activity.infos.get(city_idx).spot_infos;
        if(!(arrayList == null)){
            adapter = new Spot_Adapter((Activity) getContext(),arrayList);
            tour_spot_lv.setAdapter(adapter);
            tour_spot_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    activity.replaceFragment(position);

                }
            });
        }




        return v;
    }
}
