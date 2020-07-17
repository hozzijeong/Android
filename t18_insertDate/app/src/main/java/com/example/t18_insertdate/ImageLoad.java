package com.example.t18_insertdate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.net.URL;
import java.util.HashMap;

public class ImageLoad extends AsyncTask<Void,Void, Bitmap> {

    String urlStr;
    ImageView imageView;

    private HashMap<String, Bitmap> bitmapHashMap = new HashMap<String, Bitmap>();
    // 요청 url과 비트맵을 묶어줌, 이 url로 비트맵을 찾아야 기존에 있던 이미지를 삭제 가능

    public ImageLoad(String urlStr, ImageView imageView){
        this.urlStr = urlStr;
        this.imageView = imageView;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap bitmap = null;
        try {
            if(bitmapHashMap.containsKey(urlStr)){
                Bitmap oldBitmap = bitmapHashMap.remove(urlStr);
                if(oldBitmap != null){
                    oldBitmap.recycle();
                    oldBitmap = null;
                }
            }

            URL url = new URL(urlStr);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            // 해당 주소로 접속해서 스트림으로 받음. 그 받은걸 디코드로 바꿔서 비트맵으로 바꿔준다.

            bitmapHashMap.put(urlStr,bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return bitmap;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        imageView.setImageBitmap(bitmap);
        imageView.invalidate();
    }


}
