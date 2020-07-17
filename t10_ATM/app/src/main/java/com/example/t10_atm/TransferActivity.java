package com.example.t10_atm;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TransferActivity extends AppCompatActivity implements View.OnClickListener {
    int UserNum;
    Button transfer_ok, transfer_quite;
    EditText transfer_num, transfer_money,transfer_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Intent intent = getIntent();
        UserNum = intent.getIntExtra("UserNum",-1);

        transfer_money = findViewById(R.id.transfer_money);
        transfer_num = findViewById(R.id.transfer_num);
        transfer_pw = findViewById(R.id.transfer_pw);

        transfer_ok = findViewById(R.id.transfer_ok);
        transfer_quite = findViewById(R.id.transfer_quite);

        transfer_ok.setOnClickListener(this);
        transfer_quite.setOnClickListener(this);

    }


    public int check(String acc_num){
        int check_num = -1;
        for(int i=0; i<MainActivity.userInfos.size(); i++){
            if(acc_num.equals(MainActivity.userInfos.get(i).acc_num)){
                check_num = i;
                break;
            }
        }
        return check_num;
    }

    public void add_user_acc_info(int user_idx,String id,int money,int total_money){
        SQLiteDatabase db = openOrCreateDatabase("ATM.db",MODE_PRIVATE,null);
        db.execSQL("INSERT INTO user_acc_info(user_idx,id,money,total_money) VALUES" +
                "('"+user_idx+"','"+id+"','"+money+"','"+total_money+"')");
        db.close();
    }

    public void update_user_info(int user_idx,int money){
        SQLiteDatabase db = openOrCreateDatabase("ATM.db",MODE_PRIVATE,null);
        db.execSQL("UPDATE user_info SET money ='"+money+"' WHERE idx="+user_idx);
        db.close();
    }

    public void db_load_data(){
        SQLiteDatabase db = openOrCreateDatabase("ATM.db",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT * FROM user_info",null);
        c.moveToFirst();
        MainActivity.userInfos.clear();
        while(c.isAfterLast() == false){
            MainActivity.userInfos.add(new UserInfo(c.getInt(0),c.getString(1)
            ,c.getString(2),c.getInt(3),c.getString(4)));
            c.moveToNext();
        }
        c.close();
        db.close();
    }
    public void db_load_acc_data(int user_idx,int arr_idx){
        SQLiteDatabase db = openOrCreateDatabase("ATM.db",MODE_PRIVATE,null);
        Cursor c= db.rawQuery("SELECT * FROM user_acc_info",null);
        c.moveToFirst();
        while(c.isAfterLast() ==false){
            if(c.getInt(1) == user_idx){
                MainActivity.userInfos.get(arr_idx).acc_info.add( new Info(
                        c.getInt(1),c.getString(2),
                        c.getInt(3),c.getInt(4)
                ));
            }
            c.moveToNext();
        }
        c.close();
        db.close();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.transfer_ok){
            final String money = transfer_money.getText().toString();
            final String n = transfer_num.getText().toString();
            final String pw = transfer_pw.getText().toString();

            AlertDialog.Builder ab_transfer = new AlertDialog.Builder(this);
            ab_transfer.setTitle("메세지").setMessage("계좌번호:"+n+"님께"
                    +money+"원을 송금하시겠습니까?");

            ab_transfer.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int m = Integer.parseInt(money);
                    int trans_idx = check(n);
                    Log.d("mood","data"+trans_idx+","+pw);
                    if(MainActivity.userInfos.get(UserNum).money-m>=0 && trans_idx != -1 && pw.equals(MainActivity.userInfos.get(UserNum).pw)){

                        String trans_id = MainActivity.userInfos.get(trans_idx).id;
                        int trans_money = MainActivity.userInfos.get(UserNum).money;
                        int user_idx = MainActivity.userInfos.get(UserNum).idx;

                        String id = MainActivity.userInfos.get(UserNum).id;
                        int get_money = MainActivity.userInfos.get(trans_idx).money;
                        int user_trans_idx = MainActivity.userInfos.get(trans_idx).idx;

                        MainActivity.userInfos.get(UserNum).acc_info.add
                                (new Info(user_idx,trans_id,-m, (trans_money-m)));
                        add_user_acc_info(user_idx,trans_id,-m,(trans_money-m));

                        MainActivity.userInfos.get(trans_idx).acc_info.add
                                (new Info(user_trans_idx,id,m,(get_money+m)));
                        add_user_acc_info(user_trans_idx,id,m,(get_money+m));

                        MainActivity.userInfos.get(UserNum).money -= m;
                        update_user_info(user_idx,MainActivity.userInfos.get(UserNum).money);

                        MainActivity.userInfos.get(trans_idx).money +=m;
                        update_user_info(user_trans_idx,MainActivity.userInfos.get(trans_idx).money);

                        db_load_data();
                        db_load_acc_data(user_idx,UserNum);
                        // 어차피 UserActivity 실행할때 한번 load 해주기 때문에, 굳이 여기서
                        // 보내는 계좌의 계좌내역까지 load 할 필요는 없음.

                        Toast.makeText(getApplicationContext(),MainActivity.userInfos.get(trans_idx).id+"님 께"
                                +m+"원 송금",Toast.LENGTH_LONG).show();
                    }else{
                        if(MainActivity.userInfos.get(UserNum).money-m<0){
                            Toast.makeText(getApplicationContext(),"잔액이 부족합니다",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if(trans_idx == -1){
                            Toast.makeText(getApplicationContext(),"계좌번호가 일치하지 않습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if(!pw.equals(MainActivity.userInfos.get(UserNum).pw)){
                            Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.\n"+pw+":"+MainActivity.userInfos.get(UserNum).pw,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            });

            ab_transfer.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"취소합니다",Toast.LENGTH_SHORT).show();
                }
            });

            ab_transfer.setCancelable(true);
            ab_transfer.show();

            transfer_num.setText("");
            transfer_money.setText("");
            transfer_pw.setText("");
        }else{
            finish();
        }
    }
}
