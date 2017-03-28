package com.javen.papa.activity;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.javen.papa.R;
import com.javen.papa.fragment.TestFragment;
import com.javen.papa.fragment.girl.GirlMainFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.id_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.id_navigation)
    NavigationView mNavigationView;
    @BindView(R.id.id_main)
    FrameLayout idMain;


    private int mStatusBarColor;
    private int mColor;
    private int mAlpha = 0;

    private ActionBarDrawerToggle mDrawerToggle;

    private List<Fragment> fragmentList;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        initActionBar();
        initDrawerToggle();

        initNavigationView();

        initFragment();
        setItem(0);
        setTbColor();

    }

    private void initFragment(){
        fragmentList = new ArrayList<>();

        GirlMainFragment girlFragment = new GirlMainFragment();
        TestFragment testFragment1 = TestFragment.newInstance("测试1");
        TestFragment testFragment2 =TestFragment.newInstance("测试2");
        TestFragment testFragment3 =TestFragment.newInstance("测试3");

        fragmentList.add(girlFragment);
        fragmentList.add(testFragment1);
        fragmentList.add(testFragment2);
        fragmentList.add(testFragment3);
    }

    private void setItem(int index){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        Fragment fragment = fragmentList.get(index);
        if (fragment.isAdded()){
            ft.show(fragment);
        }else{
            ft.add(R.id.id_main,fragment);

        }
        ft.commitAllowingStateLoss();

    }

    private void initActionBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }

    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        ColorStateList csl = ContextCompat.getColorStateList(this, R.color.navigation_menu_item_color);
        mNavigationView.setItemTextColor(csl);
//        设置MenuItem默认选中项
//        mNavigationView.getMenu().getItem(0).setChecked(true);
    }


    private void setTbColor() {
        StatusBarUtil.setTranslucent(this, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_girl:
                setItem(0);
                break;
            case R.id.nav_gallery:
                Toast.makeText(this, "nav_gallery", Toast.LENGTH_SHORT).show();
                setItem(1);
                break;
            case R.id.nav_share:
                Toast.makeText(this, "nav_share", Toast.LENGTH_SHORT).show();
                setItem(2);
                break;
            case R.id.nav_send:
                Toast.makeText(this, "nav_send", Toast.LENGTH_SHORT).show();
                setItem(3);
                break;
            default:
                break;
        }
        //关闭
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }

    @Override
    protected void setStatusBar() {
        mStatusBarColor = ContextCompat.getColor(this, R.color.colorPrimary);
        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.id_drawer_layout), mStatusBarColor, mAlpha);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
