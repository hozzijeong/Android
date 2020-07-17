package com.example.alertdialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RadioButton male;
    RadioButton female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

        male.setChecked(true); // 라디오 버튼 코드로 체크

        findViewById(R.id.btn).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        // 체크가 된 상태이면 true를 반환 아니면 false를 반환, 라디오 버튼은 따로 이벤트 인터페이스를 구현하지 않음
        // OnCheckListener
        Log.d("yeah","male "+male.isChecked()+ " female "+female.isChecked());

        final EditText et = new EditText(this); // 새로운 EditText를 추가
        AlertDialog.Builder ab = new AlertDialog.Builder(this); // Dialog Builder를 인스턴스화 시킴

        ab.setView(et); // 화면에 보이도록 출력

        ab.setTitle("제목"); // 메세지 제목
        ab.setIcon(R.mipmap.ic_launcher); // 아이콘 표시
        ab.setMessage("안녕하세요"); // 메세지 내용

        // Positive가 오른쪽, Negative가 왼쪽
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"예를 선택했습니다", LENGTH_LONG).show();
                // 오른쪽 버튼을 눌렀을때 실행되는 이벤트
                String value = et.getText().toString();
            }
        });
        ab.setNegativeButton("취소 ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"아니오를 선택했습니다", LENGTH_LONG).show();
                // 왼쪽 버튼을 눌렀을때 실행되는 이벤트
            }
        });
        ab.setCancelable(true); // 버튼 말고 아무데나 눌러도 취소가 가능하다.
        ab.show();
    }
}
