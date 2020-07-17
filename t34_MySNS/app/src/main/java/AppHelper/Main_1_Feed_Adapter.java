package AppHelper;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.t34_mysns.LobbyActivity;
import com.example.t34_mysns.MainActivity;
import com.example.t34_mysns.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main_1_Feed_Adapter extends ArrayAdapter {
    private LayoutInflater lnf;
    private ArrayList<Main_Feed_Info> array;
    private Helper helper;
    private MainActivity context;
    private int position;
    public Main_1_Feed_Adapter(Activity context, ArrayList<Main_Feed_Info> array) {
        super(context, R.layout.main_1_feed_item,array);
        lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        helper = new Helper();
        this.context = (MainActivity)context;
        this.array = array;
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
        this.position = position;
        if(convertView == null) {
            convertView = lnf.inflate(R.layout.main_1_feed_item, parent, false);
            viewHolder = new RowHolder();

            viewHolder.author_iv = convertView.findViewById(R.id.feed_author_image);
            viewHolder.my_iv = convertView.findViewById(R.id.feed_my_image);
            viewHolder.author_tv = convertView.findViewById(R.id.feed_author_name);
            viewHolder.content_tv= convertView.findViewById(R.id.feed_content);
            viewHolder.comment_et= convertView.findViewById(R.id.feed_comment);
            viewHolder.insert_btn= convertView.findViewById(R.id.feed_comment_btn);
            viewHolder.like_btn= convertView.findViewById(R.id.feed_like);
//            viewHolder.hash_tag = convertView.findViewById(R.id.feed_hash_tag);
            viewHolder.feed_hash_layout = convertView.findViewById(R.id.feed_hash_layout);
            viewHolder.go_comment = convertView.findViewById(R.id.feed_go_comment);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (RowHolder)convertView.getTag();
        }
        /*
            좋아요 버튼은 true/false에 따라 색이 들어가고 안들어가고를 정함 (즉, background 색 바뀜)
            Image들은 Glide를 이용 할 예정임.
         */
        Glide.with(getContext()).load(array.get(position).author_iv_url).into(viewHolder.author_iv);
        Glide.with(getContext()).load(LobbyActivity.myinfo.url).into(viewHolder.my_iv);

        viewHolder.author_tv.setText(array.get(position).author_name);
        viewHolder.content_tv.setText(array.get(position).content);

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//                40);


        context.getLog(position+"번 hash size: "+array.get(position).hash.size());
        if (array.get(position).hash.size() > 0 && !array.get(position).check){
            for(int i=0; i<array.get(position).hash.size(); i++){
                TextView tv = new TextView(context);
                tv.setText(array.get(position).hash.get(i)+" ",TextView.BufferType.SPANNABLE);
                setSpan(tv.getText());
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                viewHolder.feed_hash_layout.addView(tv);
            }
            array.get(position).check = true;
        }

        /*
            해시테그 하나하나에 Span을 적용 한것이지, String hash_tag에는 적용한게 아니라서 그대로 상속이 안되는건가?

         */

        viewHolder.go_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.getLog("\nposition: "+position+"\nfeed_idx: "+array.get(position).board_idx);
                context.getFragment(position);
            }
        });

        viewHolder.like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(array.get(position).like){
                    array.get(position).like = false;
                    viewHolder.like_btn.setBackgroundResource(R.drawable.circle_off_shape);
                }else{
                    array.get(position).like = true;
                    viewHolder.like_btn.setBackgroundResource(R.drawable.circle_on_shape);
                }
            }
        });

        // 제일 처음에 세팅하는 것
        if(array.get(position).like){
            viewHolder.like_btn.setBackgroundResource(R.drawable.circle_on_shape);
        }else{
            viewHolder.like_btn.setBackgroundResource(R.drawable.circle_off_shape);
        }

        viewHolder.insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = viewHolder.comment_et.getText().toString();
                if(comment.length() == 0){
                    Toast.makeText(getContext(),"댓글을 입력하세요",Toast.LENGTH_LONG).show();
                }else{
                    String url = "http://hozzi.woobi.co.kr/sns/add_comment.php";
                    context.params.clear();
                    context.params.put("author",LobbyActivity.myinfo.nick);
                    context.params.put("content",comment);
                    context.params.put("board_idx",array.get(position).board_idx+"");
                    context.params.put("url",LobbyActivity.myinfo.url);
                    context.getLog("data: "+context.params);
                    context.requestVolley(url);
                    viewHolder.comment_et.setText("");
                }
            }
        });
        return convertView;
    }

    class RowHolder {
        CircleImageView author_iv,my_iv;
        Button insert_btn,go_comment;
        ImageButton like_btn;
        EditText comment_et;
        TextView author_tv,content_tv,hash_tag;
        LinearLayout feed_hash_layout;
    }

    private void setSpan(final CharSequence tv){
        final Spannable span = (Spannable) tv;
        span.setSpan(new UnderlineSpan(),0,tv.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),0,tv.length()-1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.BLUE),0,tv.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                context.showToast(tv.toString());
                String url = "http://hozzi.dothome.co.kr/sns/load_search_info.php?search_data=" + tv.toString();
                context.GetVolley(url,listener);
            }
        }, 0, tv.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            /*
                해시태그를 클릭했을 때 해당 조건을 검색해서,
                1. 게시물들의 리스트를 띄워 준다.
                2. 해당 검색어로 된 게시물들을 Custom으로 해서 띄워준다.
             */
            try {
                context.getLog("search_Response: "+response);
                JSONObject json = new JSONObject(response);
                String result = json.getString("result");
                if (result.equalsIgnoreCase("ok")) {
                    context.customs.clear();

                    context.getFragment(-4);
                    context.custom_idx = position;
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


}
