package com.example.WebView;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 13-5-17
 * Time: 上午10:23
 * To change this template use File | Settings | File Templates.
 */

/**
 * Attention:
 * Handling Android 2.3 WebView’s broken AddJavascriptInterface
 * http://www.jasonshah.com/handling-android-2-3-webviews-broken-addjavascriptinterface/
 */


public class TheJavascriptInterface {
    Context mContext;

    TheJavascriptInterface(Context c){
        mContext = c;
    }


    public String getNetworkInfo(){
        return Connectivity.getNetworkInfo(mContext).toString();
    }

    //该方法将暴露给Javascript脚本调用
    public void showToast(String name){
        Toast.makeText(mContext, name + "，您好！",
                Toast.LENGTH_LONG).show();

    }
}
