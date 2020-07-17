package Helper;

import com.android.volley.RequestQueue;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppHelper {

    public static RequestQueue requestQueue;

    public static String setEnc(String string){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(string.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for(int i=0; i<byteData.length; i++) {
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }

            string = sb.toString();
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return string;
    }
}

