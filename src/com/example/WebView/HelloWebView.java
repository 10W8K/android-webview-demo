package com.example.WebView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.*;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 13-5-9
 * Time: 下午1:37
 * To change this template use File | Settings | File Templates.
 */

public class HelloWebView extends Activity {
    WebView webview;

    //EditText textUrl;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        WebView.enablePlatformNotifications();

        //textUrl = (EditText) findViewById(R.id.textUrl);
        webview = (WebView)findViewById(R.id.webview);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        webview.setWebViewClient(new HelloWebViewClient());
        //webview.loadUrl("https://mcashier.test.alipay.net/cashier/wapcashier_login.htm");
        webview.loadUrl("http://mobilepp.stable.alipay.net");
        //webview.loadUrl("https://m.alipay.com/appIndex.htm");

        //webview.addJavascriptInterface(new MyJavascriptInterface(),"Android");

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

        /*textUrl.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                // If the event is a key-down event on the "enter" button
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    webview.loadUrl(textUrl.getText().toString());
                    //return true;
                }
                return true;

                //return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
*/
    }

    long startTime;
    long endTime;

    private class HelloWebViewClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url){
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
            Log.e("TAG", "costTime:" + (endTime - startTime));
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

    final class MyJavascriptInterface {
        /*
        public void ProcessJavascript(final String scriptname, final String args){
            mHandler.post(new Runnable(){

            });
        }
        */
    }

}