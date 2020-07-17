package Helper;


import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppHelper {
    public static RequestQueue requestQueue;

    public static String setEnc(String str){
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // 이 부분을 SHA-256, MD5로만 바꿔주면 된다.
            md.update(str.getBytes()); // "세이프123"을 SHA-1으로 변환할 예정!

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();

            for(int i=0; i<byteData.length; i++) {
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return result;
    }

    public static void getLog(String data){
        Log.d("mood",data);
    }


}

