package AppHelper;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.t34_mysns.MainActivity;
import com.example.t34_mysns.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main_3_Search_Adapter extends ArrayAdapter {
    LayoutInflater lnf;
    ArrayList<Main_Custom_Info> array;
    MainActivity context;
    public Main_3_Search_Adapter(Activity context, ArrayList<Main_Custom_Info> array) {
        super(context, R.layout.main_3_search_common_item,array);
        lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.array = array;
        this.context = (MainActivity)context;
    }

    // Adapter 에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return array.size();
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return array.get(position);
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
            convertView = lnf.inflate(R.layout.main_3_search_common_item, parent, false);
            viewHolder = new RowHolder();
            viewHolder.search_iv = convertView.findViewById(R.id.search_profile);
            viewHolder.name_tv = convertView.findViewById(R.id.search_name);
            viewHolder.nick_tv= convertView.findViewById(R.id.search_nickname);
                       convertView.setTag(viewHolder);
        }else{
            viewHolder = (RowHolder)convertView.getTag();
        }

        if(array != null){
            Glide.with(getContext()).load(array.get(position).url).into(viewHolder.search_iv);
            viewHolder.name_tv.setText(array.get(position).name);
            viewHolder.nick_tv.setText(array.get(position).nick);
        }

        return convertView;
    }

    class RowHolder {
        CircleImageView search_iv;
        TextView nick_tv,name_tv;
    }


}
