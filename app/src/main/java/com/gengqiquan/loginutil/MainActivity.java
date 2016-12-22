package com.gengqiquan.loginutil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginUtil.addLoginCheckInterceptor(new LoginUtil.LoginCheckInterceptor() {
            @Override
            public boolean isLogin() {
                return false;
            }
        });
    }
}
