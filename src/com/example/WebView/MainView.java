package com.example.WebView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 13-8-24
 * Time: 下午8:10
 * To change this template use File | Settings | File Templates.
 */
public class MainView extends Activity {
    Button button;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);


        button  = (Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){

                /* 新建一个Intent对象 */
                Intent intent = new Intent();
                intent.putExtra("name","LeiPei");
                /* 指定intent要启动的类 */
                intent.setClass(MainView.this, HelloWebView.class);
                /* 启动一个新的Activity */
                startActivity(intent);
                //HelloWebView.this.startActivity(intent);
                /* 关闭当前的Activity */
                MainView.this.finish();
            }
        });

    }
}