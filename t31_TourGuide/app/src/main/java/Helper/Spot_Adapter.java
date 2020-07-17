package Helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.t31_tourguide.R;

import java.util.ArrayList;

public class Spot_Adapter extends ArrayAdapter {
    LayoutInflater lnf;
    ArrayList<Place_Info.spot_info> arrayList;
    public Spot_Adapter(Activity context, ArrayList<Place_Info.spot_info> array) {
        super(context,R.layout.spot_item,array);
        lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = array;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayList.size();
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arrayList.get(position);
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
            convertView = lnf.inflate(R.layout.spot_item, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.spot_item_tv = convertView.findViewById(R.id.spot_item_title);
        viewHolder.spot_item_explain = convertView.findViewById(R.id.spot_item_explain);

        // 연결을 했는데 왜 null이 나오는 걸까?

        AppHelper.getLog("item_ex:"+viewHolder.spot_item_explain);
        AppHelper.getLog("spot_name:"+arrayList.get(position).spot_name);

        viewHolder.spot_item_tv.setText(arrayList.get(position).spot_name);
        viewHolder.spot_item_explain.setText(arrayList.get(position).spot_explain);

        return convertView;
    }

    class ViewHolder{
        TextView spot_item_tv,spot_item_explain;
    }
}
