package MainFragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.t31_tourguide.MainActivity;
import com.example.t31_tourguide.R;

import Helper.Advertisement;
import Helper.AppHelper;
import Helper.City_Adapter;
import Helper.MyAdapter;
import Helper.Place_Info;

public class City_List_Fragment extends Fragment {
    MainActivity activity;
    ListView city_list;
    MyAdapter adapter;
    ImageButton menu,search;
    ViewPager viewPager;
    Advertisement ad;
    TextView city_title;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
        handler.sendEmptyMessageDelayed(0,3000);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.city_list_fragment,container,false);
        city_list = v.findViewById(R.id.list_city_lv);
        menu = v.findViewById(R.id.list_menu);
        search = v.findViewById(R.id.list_search);
        viewPager = v.findViewById(R.id.list_vp);
        city_title = v.findViewById(R.id.list_title);

        AppHelper.getLog("search_condition: "+ activity.search_condition);

        if(!activity.search_condition){
            //검색했을때가 아닐때!
            city_title.setText("추천 여행지");
        }else{
            city_title.setText(activity.search_continent);
            // 검색 했을때
            MainActivity.infos.clear();
            for(int i=0; i<MainActivity.temp.size(); i++){
                if(MainActivity.temp.get(i).continent.equals(activity.search_continent)){
                    setArray_List(i);
                }
            }
//          adapter.notifyDataSetChanged();
            activity.search_condition = false;
            activity.search_continent = "";
        }

        adapter = new MyAdapter((Activity) getContext(),MainActivity.infos);
        city_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity.addFragment(position,activity.turn);
            }
        });

        ad = new Advertisement(activity.getSupportFragmentManager());
        viewPager.setAdapter(ad);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.drawer.openDrawer(GravityCompat.START);
                activity.menu_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        /*
                            position 0-> home
                                     1-> 즐겨찾기
                                     2-> 사용 방법
                                     3-> 설정
                         */
                        if(position == 0){
                            MainActivity.infos.clear();
                            for(int i=0; i<MainActivity.temp.size(); i++){
                                AppHelper.getLog("size: "+ MainActivity.temp.size());
                                setArray_List(i);
                            }
                            city_title.setText("추천 여행지");
                        }
                        else if(position == 1){
                            MainActivity.infos.clear();
                            for(int i=0; i<MainActivity.temp.size(); i++){
                                if(MainActivity.temp.get(i).star){
                                    setArray_List(i);
                                }
                            }
                            city_title.setText("즐겨찾기");
                        }else if(position ==2){

                        }else if(position ==3){

                        }
                        activity.drawer.closeDrawer(GravityCompat.START);
                        adapter.notifyDataSetChanged();

                    }
                });
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFragment(-2);
            }
        });

        return v;
    }


    public void setArray_List(int i){
        String continent = MainActivity.temp.get(i).continent;
        String city = MainActivity.temp.get(i).city;
        boolean star = MainActivity.temp.get(i).star;
        int rate = MainActivity.temp.get(i).rate;
        String url = MainActivity.temp.get(i).url;
        MainActivity.infos.add(new Place_Info(continent,city,star,rate,url));
        for(int j=0; j<MainActivity.temp.get(i).spot_infos.size(); j++){
            String spot_name = MainActivity.temp.get(i).spot_infos.get(j).spot_name;
            String spot_url = MainActivity.temp.get(i).spot_infos.get(j).url;
            String spot_explain = MainActivity.temp.get(i).spot_infos.get(j).spot_explain;
            float lat = MainActivity.temp.get(i).spot_infos.get(j).lat;
            float lng = MainActivity.temp.get(i).spot_infos.get(j).lng;
            MainActivity.infos.get(i).spot_infos.add(new Place_Info.spot_info(city,spot_name,spot_url,spot_explain,lat,lng));
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%5,false);
            handler.sendEmptyMessageDelayed(0,3000);
        }
    };


}
