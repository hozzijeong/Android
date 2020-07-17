package Helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.t26_new_atm.R;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {
    LayoutInflater lnf;
    ArrayList<Acc_Info> arr;
    public MyAdapter(Activity context, ArrayList<Acc_Info> arrayList) {
        super(context, R.layout.holder,arrayList);
        lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arr = arrayList;
    }

    // Adapter 에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arr.size();
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arr.get(position);
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    public View getView(int position, View convertView, ViewGroup parent) {
        RowHolder viewHolder; // 우리가 따로 만든  Item 클래스를 viewHolder로 객체화
        if(convertView == null) {
            convertView = lnf.inflate(R.layout.holder, parent, false);
            viewHolder = new RowHolder();
            // 이제 객체화 시킨 Item_class를 Item_layout과 연결해줌.
            // convertView로 inflate했기 때문에, layout이 convertView에 속해있음
            viewHolder.client = convertView.findViewById(R.id.client_name);
            viewHolder.transfer = convertView.findViewById(R.id.transfer_cost);
            viewHolder.total = convertView.findViewById(R.id.total_cost);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (RowHolder)convertView.getTag();
        }

        viewHolder.client.setText((arr.get(position).client_name));
        viewHolder.transfer.setText((CharSequence) arr.get(position).transfer);
        viewHolder.total.setText((CharSequence) arr.get(position).total);

        return convertView;
    }
}
