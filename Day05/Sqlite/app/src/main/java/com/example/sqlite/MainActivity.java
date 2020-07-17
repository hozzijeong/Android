package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
// 안드로이드 내부에 기본적으로 탑재되어 있는 DB
// 로컬 DB이므로 앱이나 데이터 삭제시 데이터가 같이 삭제됨
// 안드로이드를 사용하는 사람은 본인이기 때문에 DB관리를 많이 신경 쓸 필요가 없다(아직은)
// 테이블의 설계, 중요한것은 테이블과 idx의 관계...? 그것을 어떻게 만들고 다루는지?

// 삽입 삭제 검색 수정
public class MainActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbInit();
            }
        });

    }

    private void dbInit(){
        SQLiteDatabase db = openOrCreateDatabase("sqlite_test.db",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user_info("
                +"idx INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"name TEXT,"
                +"age INTEGER,"
                +"adress TEXT"
                +")"
        );
        // 테이블의 내용을 구성하는 단계 (무엇을 어떻게 넣을것인지) db와 연결해주고, user_info라는 테이블을 만들어 준것임.

        // 삽입
        db.execSQL("INSERT INTO user_info(name,age,adress) VALUES ('개똥이',20000,'서울')"); // 데이터를 저장할 때 ''를 사용함
        //테이블에서 삽입이 되면 자체적으로 한개씩 추가가됨,

        //삭제
//        db.execSQL("DELETE FROM user_info"); // 모든 것을 삭제
//        db.execSQL("DELETE FROM user_info WHERE idx = 2");// idx가 2인 것의 정보만 삭제 할 수 있음. (로우/가로줄)

        //수정
        db.execSQL("UPDATE user_info SET name = '말똥이' WHERE idx = 3"); // idx가 3인 로우를 수정(열)


        //검색
        Cursor c = db.rawQuery("SELECT * FROM user_info",null); // *는 전체를 의미함
        c.moveToFirst(); // 커서를 테이블의 처음으로 이동시킨다
        while(c.isAfterLast() == false){ // c가 마지막이 아니라면 계속 돌림. 마지막이면 안돌림
            Log.d("mood",
                    "name: "+c.getString(1)
                    +" age: "+c.getInt(2)
                    +" adress: " + c.getString(3));
                    c.moveToNext(); // 커서를 다음 커서로 이동
        }
        c.close();
        db.close();
        // 데이터를 불러서 저장할때는 ArrayList<class>의 형태에 맞게 잘 끼워 맞춘다.
        // 계속해서 실행하면 누적되서 데이터가 저장이됨.

        // open과 close를 한꺼번에 적고 사이를 벌려서 작업하라
    }
}
