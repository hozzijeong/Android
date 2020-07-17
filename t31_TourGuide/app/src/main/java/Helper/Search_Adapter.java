package Helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.t31_tourguide.R;

import java.util.ArrayList;

public class Search_Adapter extends ArrayAdapter {
    LayoutInflater lnf;
    ArrayList<String> array;
    public Search_Adapter(Activity context,ArrayList<String> array) {
        super(context,R.layout.search_item,array);
        lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.array = array;
    }

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
        ViewHolder viewHolder; // 우리가 따로 만든  Item 클래스를 viewHolder로 객체화
        if(convertView == null) {
            convertView = lnf.inflate(R.layout.search_item, parent, false);
        }
        viewHolder = new ViewHolder();
        viewHolder.search_item_tv = convertView.findViewById(R.id.search_item_tv);
//        else{
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
        // 왜 viewHolder.search_item_tv가 null이 되는거지?
        // 그 뭐야 editText만 클릭하면 왜 null이 되는걸까?
        // 뭐가 차이점이 있는걸까?
        viewHolder.search_item_tv.setText(array.get(position));

        return convertView;
    }

    class ViewHolder{
        TextView search_item_tv;
    }

}
