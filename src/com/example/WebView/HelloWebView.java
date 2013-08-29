package com.example.WebView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.webkit.*;
import android.widget.EditText;


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
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        setContentView(R.layout.hellowebview);

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
        //webview.requestFocus(View.FOCUS_DOWN);




        webview.loadUrl("http://d.alipay.com/appdebug/demo3.htm");
        //webview.loadUrl("http://wappaygw.alipay.com/cashier/wapcashier_login.htm");
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



    }

}