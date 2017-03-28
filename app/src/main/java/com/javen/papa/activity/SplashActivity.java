package com.javen.papa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jaeger.library.StatusBarUtil;
import com.javen.papa.R;

public class SplashActivity extends BaseActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        setContentView(R.layout.activity_index);
        setStatusBar();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext,ResideActivity.class));
                //处理淡出式打开新的 Activity
                overridePendingTransition(R.anim.fade, R.anim.hold);
                //左右滑入式
//                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);

                finish();
            }
        },1000*2);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucent(this, 0);
    }
}
