package AppHelper;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.t34_mysns.LobbyActivity;
import com.example.t34_mysns.MainActivity;
import com.example.t34_mysns.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main_2_Comment_Adapter extends ArrayAdapter {
    LayoutInflater lnf;
    ArrayList<Main_Board_Comment> array;
    MainActivity context;
    public Main_2_Comment_Adapter(Activity context, ArrayList<Main_Board_Comment> array) {
        super(context, R.layout.main_2_comment_item,array);
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
            convertView = lnf.inflate(R.layout.main_2_comment_item, parent, false);
            viewHolder = new RowHolder();
            viewHolder.author_iv = convertView.findViewById(R.id.comment_author_iv);
            viewHolder.author_tv = convertView.findViewById(R.id.comment_author);
            viewHolder.content_tv= convertView.findViewById(R.id.comment_content);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (RowHolder)convertView.getTag();
        }

        context.getLog("작성자: "+array.get(position).author);
        context.getLog("댓글: "+array.get(position).comment);
        context.getLog("프로필 url: "+array.get(position).url);

        if(array != null){
            Glide.with(getContext()).load(array.get(position).url).into(viewHolder.author_iv);
            viewHolder.author_tv.setText(array.get(position).author);
            viewHolder.content_tv.setText(array.get(position).comment);
        }else{
            viewHolder.content_tv.setText("댓글이 없습니다");
        }

        return convertView;
    }

    class RowHolder {
        CircleImageView author_iv;
        TextView author_tv,content_tv;
    }


}
