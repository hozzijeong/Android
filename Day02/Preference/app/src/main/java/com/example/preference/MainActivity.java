package com.example.preference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView myinfo;
    EditText name;




//    EditText adress;
    /*
    이미지 만드는법
      1. res폴더 아래 drawable-xxhdpi 폴더를 만든다.(글자는 영어 )
      2. 이미지를 저장해서 새로 만든 폴더에 옮긴다.
         이름규칙) 영어 소문자로 시작하여, 영어 소문자, 언더라인, 숫자를 주로 많이 사용
      3. R.drawable.(이미지 파일이름)
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.save).setOnClickListener(this);
        myinfo = findViewById(R.id.myinfo); // textView
        name = findViewById(R.id.name); // EditText
//        adress = findViewById(R.id.adress);
        SharedPreferences pre = getSharedPreferences("myNew",MODE_PRIVATE);
        String data = pre.getString("name",null);
        if(data == null){
            myinfo.setText("저장된 글이 없습니다.");
        }else{
            myinfo.setText(data);
        }
    }

    /*
    1. 앱이 시작하면 화면에 텍스트뷰, 에딧 텍스트, 버튼이 있음
    2. 에딧 텍스트에 글을 입력하고 버튼을 누르면 프리버런스에 저장이됨
    3. 앱이 시작할때 프리퍼런스에 저장된 글이 없으면 저장된 글이 없다고 나오고
    있으면 저장된 것을 보여줌

    지금 까지 배운것들을 활용하면 다양한 게임을 만들 수 있음.
     */
    @Override
    public void onClick(View v) {
        String info = myinfo.getText().toString();
        if(v.getId() == R.id.save){
            SharedPreferences pre = getSharedPreferences("myNew",MODE_PRIVATE);
            SharedPreferences.Editor edit = pre.edit();
            edit.putString("name",name.getText().toString());
//            edit.putString("adress",adress.getText().toString());
            edit.commit();
        }
    }
}