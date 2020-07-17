package com.example.t10_atm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    int UserNum; // static ArrayList에 저장된 유저의 번호
    final static int RESULT_USER_CODE = 2002;
    Button input,withdrawal,transfer,check,logout,unjoin;
    TextView user_tv;

    /*
     새로운 데이터를 load 할때, 초기화를 하는데 그 때 계좌 내역도 같이 초기화가 됨.
     따라서 초기화를 시키고 나면 계좌 내역도 같이 업로드 해줘야함. (계좌 내역을 실질적으로 보는
     UserActivity 에서만 설정할것)

     문제 1가지가 더 있는데, user_infos 에 저장되는 데이터의 값들(순서)은 유니크한 값이 아니기때문에,
     새로운 데이터를 초기화 하고, 다시 데이터를 업로드 할때 이전에 저장되어 있던 값들이 같이 딸려오는 경우가 있음.

     따라서, 계좌 내역 데이터를 업로드 할때는, 테이블에 설정해 놓은 user_idx 값과, 새로 만든 userinfos.get(UserNum)
     .idx 값이 맞는 것들만 데이터를 추가해서 로드하고, 저장하면 됨.
     --> user_acc_info 의 데이터는 되게 더럽게 저장되어 있겠지만, 번호만 잘 맞추면 됨.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        UserNum = intent.getIntExtra("UserNum",-1);

        user_tv = findViewById(R.id.user_tv);
        user_tv.setText(MainActivity.userInfos.get(UserNum).id+"님\n"+
                MainActivity.userInfos.get(UserNum).acc_num);

        input = findViewById(R.id.user_input);
        withdrawal = findViewById(R.id.user_withdrawal);
        transfer =findViewById(R.id.user_transfer);
        check = findViewById(R.id.user_check);
        logout = findViewById(R.id.user_logout);
        unjoin = findViewById(R.id.user_unjoin);

        input.setOnClickListener(this);
        withdrawal.setOnClickListener(this);
        transfer.setOnClickListener(this);
        check.setOnClickListener(this);
        logout.setOnClickListener(this);
        unjoin.setOnClickListener(this);

        db_load_acc_data(MainActivity.userInfos.get(UserNum).idx);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for(int j=0; j<MainActivity.userInfos.get(UserNum).acc_info.size(); j++){
            Log.d("mood","user_acc_info:"
                    +MainActivity.userInfos.get(UserNum).acc_info.get(j).user_idx+"/"
                    +MainActivity.userInfos.get(UserNum).acc_info.get(j).name+"/"
                    +MainActivity.userInfos.get(UserNum).acc_info.get(j).money+"\n"
                    );
        }
    }

    public void add_user_acc_info(int user_idx, String id, int money, int total_money){
        SQLiteDatabase db = openOrCreateDatabase("ATM.db",MODE_PRIVATE,null);
        db.execSQL("INSERT INTO user_acc_info(user_idx,id,money,total_money)"
            +"VALUES('"+user_idx+"','"+id+"','"+money+"','"+total_money+"')");
        db.close();
    }

    public void upadate_user_info(int money,int idx){
        SQLiteDatabase db = openOrCreateDatabase("ATM.db",MODE_PRIVATE, null);
        db.execSQL("UPDATE user_info SET money ='"+money+"'WHERE idx="+idx);
        db.close();
    }

    public void db_load_data(){
        SQLiteDatabase db = openOrCreateDatabase("ATM.db",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT * FROM user_info",null);
        c.moveToFirst();
        MainActivity.userInfos.clear(); //여기서 clear를 해버리니까 user_acc_info가 남지 않는것...
        while(c.isAfterLast() == false){
            MainActivity.userInfos.add(new UserInfo(c.getInt(0),c.getString(1),
                    c.getString(2), c.getInt(3),c.getString(4)));
            c.moveToNext();
        }
        c.close();
        db.close();
    }

    public void db_load_acc_data(int user_idx){
        SQLiteDatabase db = openOrCreateDatabase("ATM.db",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT * FROM user_acc_info",null);
        c.moveToFirst();
        while(c.isAfterLast() ==false){
            if(c.getInt(1) == user_idx){
                MainActivity.userInfos.get(UserNum).acc_info
                        .add(new Info(c.getInt(1),c.getString(2),
                                c.getInt(3),c.getInt(4)));

                Log.d("mood","user_idx: "+c.getInt(1)+" id: "+c.getString(2)
                +"움직인 money: "+c.getInt(3)+" 결과: "+c.getInt(4));
            }
            c.moveToNext();
        }
        c.close();
        db.close();
    }

    public void delete_user_data(int idx){
        SQLiteDatabase db = openOrCreateDatabase("ATM.db",MODE_PRIVATE,null);
        db.execSQL("DELETE FROM user_info WHERE idx = "+idx);
        db.close();
    }


    public void delete_acc_info(int user_idx){
        SQLiteDatabase db = openOrCreateDatabase("ATM.db",MODE_PRIVATE,null);
        Cursor c= db.rawQuery("SELECT * FROM user_acc_info",null);
        c.moveToFirst();
        while(c.isAfterLast()== false){
            int idx = c.getPosition();
            Log.d("mood","user_idx: "+user_idx+" idx: "+idx+" c의 1번: "+c.getInt(1));
            if(c.getInt(1 ) == user_idx){
                db.execSQL("DELETE FROM user_acc_info WHERE idx="+idx);
            }
            c.moveToNext();
        }

        c.close();
        db.close();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.user_input:
                AlertDialog.Builder ab_input = new AlertDialog.Builder(this);
                final EditText et = new EditText(getApplicationContext());
                ab_input.setTitle("입금").setMessage("입금 금액을 입력하세요");
                et.setHint("금액 입력(원)");
                ab_input.setView(et); // EditText를 보여줌
                ab_input.setPositiveButton("입금", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int user_idx = MainActivity.userInfos.get(UserNum).idx;
                        int input_money = Integer.parseInt(et.getText().toString());
                        String id = MainActivity.userInfos.get(UserNum).id;
                        int money = MainActivity.userInfos.get(UserNum).money;

                        MainActivity.userInfos.get(UserNum).acc_info.
                                add(new Info(user_idx,id,input_money,(money+input_money)));

                        add_user_acc_info(MainActivity.userInfos.get(UserNum).idx,id,input_money,(money+input_money));

                        // 입금했을때, userinfos.acc_info에 추가되야함. 여기서 이름과 금액을 확인해야됨.
                        // 애초에 입금을 할때 결과가 반영되기 전에 값을 넣음.
                        // 초기화를 해서 값이 초기화 됐다고 쳐도, 애초에 user_acc_info 테이블에는 기존 값이 저장되어 있는데?

                        MainActivity.userInfos.get(UserNum).money += input_money;
                        upadate_user_info(MainActivity.userInfos.get(UserNum).money,
                                MainActivity.userInfos.get(UserNum).idx);

                        db_load_data();
                        db_load_acc_data(user_idx);

                        Toast.makeText(getApplicationContext(),et.getText().toString()+"원 입금 완료",Toast.LENGTH_LONG).show();
                    }
                });
                ab_input.setNegativeButton("취소" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ab_input.setCancelable(false);
                ab_input.show();
                break;

            case R.id.user_withdrawal:
                AlertDialog.Builder ab_withdrawal = new AlertDialog.Builder(this);
                ab_withdrawal.setTitle("출금").setMessage("금액 입력");

                final EditText et_withdrawal = new EditText(getApplicationContext());

                et_withdrawal.setHint("출금 금액을 입력하세요");
                ab_withdrawal.setView(et_withdrawal);
                ab_withdrawal.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int withdrawal = Integer.parseInt(et_withdrawal.getText().toString());

                        if(MainActivity.userInfos.get(UserNum).money - withdrawal >=0){

                            int user_idx = MainActivity.userInfos.get(UserNum).idx;
                            String id = MainActivity.userInfos.get(UserNum).id;
                            int money = MainActivity.userInfos.get(UserNum).money;
                            Log.d("mood","출금 금액: "+withdrawal + "user_idx: "+user_idx);

                            MainActivity.userInfos.get(UserNum).acc_info.add(
                                    new Info(user_idx,id,-withdrawal,(money-withdrawal)));

                            add_user_acc_info(user_idx,id,-withdrawal,(money-withdrawal));

                            MainActivity.userInfos.get(UserNum).money -= withdrawal;

                            upadate_user_info(MainActivity.userInfos.get(UserNum).money,
                                    MainActivity.userInfos.get(UserNum).idx);

                            db_load_data();
                            db_load_acc_data(user_idx);

                            Toast.makeText(getApplicationContext(),withdrawal+"원 출금 완료",
                                    Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"금액 부족...",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

                ab_withdrawal.setCancelable(false);
                ab_withdrawal.show();
                break;

            case  R.id.user_transfer:
                Intent intent = new Intent(this,com.example.t10_atm.TransferActivity.class);
                intent.putExtra("UserNum",UserNum);
                startActivity(intent);
                break;
            case R.id.user_check:
                Intent intent2 = new Intent(this,com.example.t10_atm.RemainActivity.class);
                intent2.putExtra("UserNum",UserNum);
                startActivity(intent2);
                break;

            case R.id.user_unjoin:
                AlertDialog.Builder ab_unjoin = new AlertDialog.Builder(this);
                ab_unjoin.setTitle("메세지").setMessage("탈퇴하면 모든 회원정보는 사라지게 됩니다." +
                        "\n정말탈퇴 하시겠습니까?");
                ab_unjoin.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                         delete_acc_info(MainActivity.userInfos.get(UserNum).idx);

                         delete_user_data(MainActivity.userInfos.get(UserNum).idx);

                         MainActivity.userInfos.remove(UserNum);

                         db_load_data();

                         Toast.makeText(getApplicationContext(),"탈퇴 완료",Toast.LENGTH_LONG).show();
                         setResult(RESULT_USER_CODE);
                         finish();
                    }
                });
                ab_unjoin.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ab_unjoin.setCancelable(false);
                ab_unjoin.show();
                break;

            case R.id.user_logout:
                setResult(RESULT_USER_CODE);
                finish();
                break;
        }
    }
}
