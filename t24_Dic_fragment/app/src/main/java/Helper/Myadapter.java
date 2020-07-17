package Helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.t24_dic_fragment.List_Fragment;
import com.example.t24_dic_fragment.R;

import java.util.ArrayList;

// 다양한 배열을 받는 것은 할 수는 있지만 굳이 하지는 않는다. 각자 적용되는 getView가 다르기 때문에, 하니씩 새롭게 만들어 줘야하는 것이
// 비효율 적이기 때문이다.
public class Myadapter extends ArrayAdapter {
    LayoutInflater lnf;
    ArrayList<Voca> arr;
    public Myadapter(Activity context, ArrayList<Voca> arrayList) {
        super(context,R.layout.word_list,arrayList);
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
            convertView = lnf.inflate(R.layout.word_list, parent, false);
            viewHolder = new RowHolder();
            // 이제 객체화 시킨 Item_class를 Item_layout과 연결해줌.
            // convertView로 inflate했기 때문에, layout이 convertView에 속해있음
            viewHolder.turn = convertView.findViewById(R.id.list_turn);
            viewHolder.eng = convertView.findViewById(R.id.list_eng);
            viewHolder.kor = convertView.findViewById(R.id.list_kor);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (RowHolder)convertView.getTag();
        }

        viewHolder.turn.setText((position+1)+".");
        viewHolder.eng.setText((CharSequence) arr.get(position).eng);
        viewHolder.kor.setText((CharSequence) arr.get(position).kor);

        return convertView;
    }
}
