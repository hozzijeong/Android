package Helper;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.t31_tourguide.MainActivity;

import MainFragment.City_Image_Fragment;
import MainFragment.Tour_Spot_Preview_Fragment;

public class City_Adapter extends FragmentStatePagerAdapter {
    private MainActivity activity;
    int city_idx;
    private boolean possible;
    Tour_Spot_Preview_Fragment preview;

    public City_Adapter(@NonNull FragmentManager fm) {
        super(fm); //  java.lang.IndexOutOfBoundsException: Index: 0, Size: 0 at Helper.City_Adapter.<init>(City_Adapter.java:20)
        activity = new MainActivity();
        // 새롭게 load 했기 때문에 실제 로 infos에 있는 값도 0에 수렴함. 새로운 값을 만들거나 load를 다시 해줘야한다!
        city_idx = MainActivity.pos; // 이게 왜 0이 나올까? activity.pos는 값이 1이 나오는데
        if(MainActivity.infos.get(city_idx).spot_infos == null){
            possible = false;
        }else{
            possible = true;
        }
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        City_Image_Fragment city;

        if(possible){
            city = new City_Image_Fragment();
//            preview = new Tour_Spot_Preview_Fragment();
//            preview.url = MainActivity.infos.get(city_idx).spot_infos.get(position).url;
//            preview.page= position+"/"+MainActivity.infos.get(city_idx).spot_infos.size();

            city.url = MainActivity.infos.get(city_idx).spot_infos.get(position).url;
        }else{
//            preview = null;
            city = null;
        }

        // 여기서도 제일 처음 이미지를 어떻게 구현할 것인가???
//        return preview;
        return  city;
    }


    @Override
    public int getCount() {
        int size;
//        왜 size가 0이 나올까?
        if (possible){
            size = MainActivity.infos.get(city_idx).spot_infos.size();
        }else{
            size = 0;
        }

        return size;
    }
}
