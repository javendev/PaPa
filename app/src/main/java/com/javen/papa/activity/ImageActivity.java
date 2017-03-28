package com.javen.papa.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;
import com.javen.papa.R;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.view_need_offset)
    CoordinatorLayout mViewNeedOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 设置右滑动返回
        initSlidr();
        StatusBarUtil.setTranslucentForImageView(this, 0, mViewNeedOffset);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, mViewNeedOffset);
    }

    private void initSlidr(){
        int primary = ContextCompat.getColor(this,R.color.colorPrimary);
        int secondary = ContextCompat.getColor(this,R.color.colorAccent);
        SlidrConfig config = new SlidrConfig.Builder()
                .primaryColor(primary)//滑动时状态栏的渐变结束的颜色
                .secondaryColor(secondary)//滑动时状态栏的渐变开始的颜色
                .scrimColor(Color.BLACK)//滑动时Activity之间的颜色
                .position(SlidrPosition.LEFT)//从左边滑动
                .scrimStartAlpha(0.8f)//滑动开始时两个Activity之间的透明度
                .scrimEndAlpha(0f)//滑动结束时两个Activity之间的透明度
                .velocityThreshold(5f)//超过这个滑动速度，忽略位移限定值就切换Activity
                .distanceThreshold(.35f)//滑动位移占屏幕的百分比，超过这个间距就切换Activity
                .build();

        Slidr.attach(this, config);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
