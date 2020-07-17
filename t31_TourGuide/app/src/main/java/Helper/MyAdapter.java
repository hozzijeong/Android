package Helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.example.t31_tourguide.MainActivity;
import com.example.t31_tourguide.R;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {
    MainActivity activity;
    LayoutInflater lnf;
    ArrayList<Place_Info> infos;
    public MyAdapter(Activity context,ArrayList<Place_Info> arrayList) {
        super(context,R.layout.rowholder,arrayList);
        lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.infos = arrayList;
        activity = new MainActivity();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return infos.size();
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return infos.get(position);
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    public View getView(final int position, View convertView, ViewGroup parent) {
        final RowHolder viewHolder; // 우리가 따로 만든  Item 클래스를 viewHolder로 객체화
        if(convertView == null) {
            // item레이아웃을 받아옴
            convertView = lnf.inflate(R.layout.rowholder, parent, false);
            viewHolder = new RowHolder();
            // 이제 객체화 시킨 Item_class를 Item_layout과 연결해줌.
            // convertView로 inflate했기 때문에, layout이 convertView에 속해있음
            viewHolder.image = convertView.findViewById(R.id.list_image);
            viewHolder.continent = convertView.findViewById(R.id.list_continent);
            viewHolder.city= convertView.findViewById(R.id.list_city);
            viewHolder.star = convertView.findViewById(R.id.list_star);
            convertView.setTag(viewHolder);


        }else{
            viewHolder = (RowHolder)convertView.getTag();
        }
        Glide.with(getContext()).load(infos.get(position).url).into(viewHolder.image);

        viewHolder.star.setFocusable(false);
        viewHolder.star.setFocusableInTouchMode(false);

        viewHolder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MainActivity.infos.get(position).star){
                    MainActivity.infos.get(position).star = true;
                    viewHolder.star.setImageResource(R.drawable.top_bookmark_on);
                }else{
                    MainActivity.infos.get(position).star = false;
                    viewHolder.star.setImageResource(R.drawable.top_bookmark_off);
                }
            }
        });
        viewHolder.continent.setText(infos.get(position).continent);
        if(viewHolder.continent.getText().toString().equals(activity.world_continent[0])){
            viewHolder.continent.setBackgroundColor(Color.parseColor("#ff0000"));
        }else if (viewHolder.continent.getText().toString().equals(activity.world_continent[1])){
            viewHolder.continent.setBackgroundColor(Color.parseColor("#ffff00"));
        }else if (viewHolder.continent.getText().toString().equals(activity.world_continent[2])){
            viewHolder.continent.setBackgroundColor(Color.parseColor("#00ff00"));
        }else if (viewHolder.continent.getText().toString().equals(activity.world_continent[3])){
            viewHolder.continent.setBackgroundColor(Color.parseColor("#ff00ff"));
        }else if (viewHolder.continent.getText().toString().equals(activity.world_continent[4])){
            viewHolder.continent.setBackgroundColor(Color.parseColor("#f0f000"));
        }else if (viewHolder.continent.getText().toString().equals(activity.world_continent[5])){
            viewHolder.continent.setBackgroundColor(Color.parseColor("#ff00f0"));
        }
        viewHolder.city.setText(infos.get(position).city);

        return convertView;
    }

}

