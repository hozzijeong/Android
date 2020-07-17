package com.example.webview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
/*
    네이티브 코드
    안드로이드에서 html코드를 바꿨다는 것이 의미(네이티브 코드)
 */
public class MainActivity extends AppCompatActivity {
    Button mainBtn;
    WebView mainWv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainBtn = findViewById(R.id.main_btn);
        mainWv = findViewById(R.id.main_wv);

        WebSettings webSettings = mainWv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mainWv.setWebChromeClient(new MyChromeClient());
        mainWv.setWebViewClient(new WebViewClient());

        /*
            html -> android 를 호출 힐때.

         */

        mainWv.addJavascriptInterface(new JavaScriptMethod(),"sample");

        mainWv.loadUrl("file:///android_asset/www/sample.html");


        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mainWv.loadUrl("https://www.naver.com/");
                    mainWv.loadUrl("javascript:changeFace()");
            }
        });
    }

    public class JavaScriptMethod{

        @android.webkit.JavascriptInterface
        public void clickOnFace(){
            handler.sendEmptyMessage(0);
        }
    }

    /*
        안드로이드와 html에서 돌아가는 Thread가 서로 다르기 때문에 handler를 사용하지 않으면
        적용이 되지 않음.
     */


    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            mainBtn.setText("Hello");
        }
    };


    class myWebViewClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            /*
                페이지를 처음 읽을때 뜨는 창.
             */
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            /*
                페이지가 실행될때 나오는 모든 데이터들을 나타내줌 -> url
            */
        }
    }

    class MyChromeClient extends WebChromeClient{
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm(); // 안드로이드 팝업창을 웹에서 호출 할 수 있음
            return true;
        }
    }

}
