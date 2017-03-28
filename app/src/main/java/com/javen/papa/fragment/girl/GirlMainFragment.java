package com.javen.papa.fragment.girl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.javen.papa.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Javen on 2017/3/26.
 */

public class GirlMainFragment extends Fragment implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.id_girl_fl)
    FrameLayout idGirlFl;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    Unbinder unbinder;

    private ArrayList<Fragment> fragments;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate....");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        System.out.println("onCreateView....");
        View view = inflater.inflate(R.layout.fragment_girl_main, null);
        unbinder = ButterKnife.bind(this, view);
        //设置导航栏模式
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        //设置导航栏背景模式
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, getResources().getString(R.string.girl_home)).setActiveColorResource(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, getResources().getString(R.string.girl_tags)).setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, getResources().getString(R.string.girl_setting)).setActiveColorResource(R.color.black))
                .setFirstSelectedPosition(0)
                .initialise();

        fragments = getFragments();

        setDefaultFragment();

        bottomNavigationBar.setTabSelectedListener(this);
        return view;
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.id_girl_fl, fragments.get(0));
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TagsFragment());
        fragments.add(new SettingFragment());
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {
        System.out.println("onTabSelected>"+position);
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    ft.replace(R.id.id_girl_fl, fragment);
                } else {
                    ft.add(R.id.id_girl_fl, fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        System.out.println("onTabUnselected>"+position);

        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
