package MainFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.t31_tourguide.MainActivity;
import com.example.t31_tourguide.R;

import java.util.ArrayList;

import Helper.AppHelper;
import Helper.Search_Adapter;

public class Search_Fragment extends Fragment {
    EditText search_et;
    ImageButton search_btn;
    ListView search_lv;
    Search_Adapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> temp = new ArrayList<>();
    MainActivity activity;
    City_List_Fragment city;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
        city = new City_List_Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment,container,false);
        search_et = v.findViewById(R.id.search_et);
        search_btn = v.findViewById(R.id.search_btn);
        search_lv = v.findViewById(R.id.search_lv);
        setArrayList();

        temp.addAll(arrayList);

        adapter = new Search_Adapter((Activity) getContext(),temp);
        search_lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        search_btn.setOnClickListener(new View.OnClickListener() {
            //fragment 에서 fragment로 접근하는 방법은 없나? new Fragment? 하지만 데이터를 다시 다 설정해야하는데?
            // getActivity 처럼 데이터 그대로 받아오는 방법은 없는건가...?
            @Override
            public void onClick(View v) {
                activity.search_continent = search_et.getText().toString();
                activity.replaceFragment(-1);
                activity.search_condition = true;
                AppHelper.getLog("continent: "+activity.search_continent+" condition: "+  activity.search_condition) ;
            }
        });


        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = search_et.getText().toString();
                search(text);
            }
        });


        search_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search_et.setText(temp.get(position));
            }
        });

        return v;
    }

    private void search(String text){
       temp.clear();
        // 창을 보여줄 list가 따로 존재하고, 어댑터랑 연결할 ArrayList는 건드리지 않는걸로
        if(text.length() == 0){
            temp.addAll(arrayList);
        }else{
            for(int i=0; i<arrayList.size();i++){
                if(arrayList.get(i).toLowerCase().contains(text)){
                    temp.add(arrayList.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    void setArrayList(){
        arrayList.clear();
        for(int i=0; i<activity.world_continent.length;i++){
            arrayList.add(activity.world_continent[i]);
        }
    }
}
