package com.example.WebView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.webkit.*;
import android.widget.EditText;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 13-5-9
 * Time: 下午1:37
 * To change this template use File | Settings | File Templates.
 */

public class HelloWebView extends Activity {
    WebView webview;

    EditText textUrl;

    boolean javascriptInterfaceBroken;

    ProgressDialog mypProgressBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * For IDEA :
         *   Tools -> Android -> Monitor(DDMS included)
         *   可以打开LogCat
         */
        //getNetworkInfo




        //This code makes the current Activity Full-Screen. No Status-Bar or anything except the Activity-Window!
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        setContentView(R.layout.main);

        WebView.enablePlatformNotifications();

        textUrl = (EditText) findViewById(R.id.textUrl);
        webview = (WebView)findViewById(R.id.webview);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);




        /**
         * Can't type inside a Web View
         * http://stackoverflow.com/questions/2653923/cant-type-inside-a-web-view
         */
        webview.requestFocus(View.FOCUS_DOWN);

        //webview.setWebViewClient(new HelloWebViewClient());
        //webview.loadUrl("https://mcashier.test.alipay.net/cashier/wapcashier_login.htm");

        mypProgressBar = ProgressDialog.show(HelloWebView.this, "Loading...", "加载中...");

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("UserLog::pageStarted:",url);
                textUrl.setText(url.toString());
                mypProgressBar.show();
                mypProgressBar.onStart();
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                textUrl.setText(url.toString());
                Log.d("UserLog::pageFinished:",url);
                if (mypProgressBar.isShowing()) {
                    mypProgressBar.cancel();
                }
            }
            public void onLoadResource(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("UserLog::LoadResource:",url);
            }
        });

        webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {  //表示按返回键
                        webview.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
        //DebugServiceClient.attachWebView(webview, this);
        //webview.loadUrl("content://jsHybugger.org/https://m.alipay.com/appIndex.htm");
        //webview.loadData("aaaa", "text/html", null);
        //webview.loadUrl("http://mobilepp.stable.alipay.net");
        //webview.loadUrl("http://d.alipay.com/appdebug/demo3.htm");
        webview.loadUrl("http://wappaygw.alipay.com/cashier/wapcashier_login.htm");
        //http://portal.manjushri.alibaba.com/portal/portal/init.jspa?instanceCode=ALIR00001297&scenceCode=SCEN00002265&digest=8937710d31bd8f6a991c8133c8a7696d
        //http://onlinehelp.alipay.com/portal/portal/init.jspa?instanceCode=ALIR00001297&scenceCode=SCEN00003363&digest=cf0b4a3d4f5af0c15c4232fb7d4c3fb5

        //webview.loadUrl("https://m.alipay.com/appIndex.htm");

        //FileInputStream inStream = this.openFileInput("");
        //webview.loadUrl("bootstrap.html");


        WebSettings settings = webview.getSettings();
        //启用javascript
        settings.setJavaScriptEnabled(true);
        //Sets whether the database storage API is enabled.
        settings.setDatabaseEnabled(true);
        //禁用App缓存
        settings.setAppCacheEnabled(false);
        //Overrides the way the cache is used.
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        String databasePath = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(databasePath);

        // Determine if JavaScript interface is broken.
        // For now, until we have further clarification from the Android team,
        // use version number.
        Log.d("UserLog::android version",Build.VERSION.RELEASE);
        try {
            if ("2.3".equals(Build.VERSION.RELEASE)) {
                javascriptInterfaceBroken = true;
            }
        } catch (Exception e) {
            // Ignore, and assume user javascript interface is working correctly.
        }



        final Activity activity = this;
        webview.setWebChromeClient(new WebChromeClient(){

            public void onProgressChanged(WebView view, int progress){
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }


            public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota,
                                                long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(5 * 1024 * 1024);
            }
        });
        /**
         * Thanks to:
         * http://stackoverflow.com/questions/4293965/android-webview-focus-problem
        */
        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }
        });


        textUrl.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                // If the event is a key-down event on the "enter" button
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    webview.loadUrl(textUrl.getText().toString());
                    return true;
                }else{
                    return false;
                }


                //return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }

    long startTime;
    long endTime;

    private class HelloWebViewClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url){
            textUrl.setText(url.toString());
            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            //startActivity(intent);
            //return true;
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
            super.onReceivedError(view, errorCode, description, failingUrl);
            //handler.proceed(); // Ignore SSL certificate errors
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }


        // 开始加载
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            startTime = System.currentTimeMillis();
        }

        // 结束加载
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            endTime = System.currentTimeMillis();
            Log.e("TAG", "UserLog::costTime:" + (endTime - startTime));

            if(javascriptInterfaceBroken){
                /*String handleGingerbreadStupidity=
                        "javascript:function openQuestion(id) { window.location='http://jshandler:openQuestion:'+id; }; "
                                + "javascript: function handler() { this.openQuestion=openQuestion; }; "
                                + "javascript: var jshandler = new handler();";
                view.loadUrl(handleGingerbreadStupidity);*/
            }
        }


        /*
        * (non-Javadoc)
        *
        * @see
        * android.webkit.WebViewClient#onLoadResource(android.webkit.WebView,
        * java.lang.String)
        */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.i("TAG", "url : " + url);
        }
    }
}