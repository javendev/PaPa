package com.javen.papa.activity;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.javen.papa.R;
import com.javen.papa.event.SkinChangeEvent;
import com.javen.papa.fragment.TestFragment;
import com.javen.papa.fragment.girl.GirlMainFragment;
import com.javen.papa.fragment.girl.SettingFragment;
import com.javen.papa.theme.ColorRelativeLayout;
import com.javen.papa.theme.ColorUiUtil;
import com.javen.papa.theme.ColorView;
import com.javen.papa.theme.Theme;
import com.javen.papa.utils.DimenUtils;
import com.javen.papa.utils.PreUtils;
import com.javen.papa.utils.SystemUtils;
import com.javen.papa.utils.ThemeUtils;
import com.javen.papa.widget.ResideLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.foundation_icons_typeface_library.FoundationIcons;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ResideActivity extends com.javen.papa.base.BaseActivity implements ColorChooserDialog.ColorCallback {
    @BindView(R.id.avatar) ImageView mAvatar;
    @BindView(R.id.desc) TextView mDesc;
    @BindView(R.id.all) TextView mAll;
    @BindView(R.id.fuli) TextView mFuli;
    @BindView(R.id.android) TextView mAndroid;
    @BindView(R.id.ios) TextView mIos;
    @BindView(R.id.video) TextView mVideo;
    @BindView(R.id.front) TextView mFront;
    @BindView(R.id.resource) TextView mResource;
    @BindView(R.id.app) TextView mApp;
    @BindView(R.id.more) TextView mMore;
    @BindView(R.id.scrollView) ScrollView mScrollView;
    @BindView(R.id.about) TextView mAbout;
    @BindView(R.id.theme) TextView mTheme;
    @BindView(R.id.menu) ColorRelativeLayout mMenu;
    @BindView(R.id.status_bar) ColorView mStatusBar;
    @BindView(R.id.icon) ImageView mIcon;
    @BindView(R.id.title) TextView mTitle;
    @BindView(R.id.container) FrameLayout mContainer;
    @BindView(R.id.resideLayout) ResideLayout mResideLayout;
    ResideLayout resideLayout;

    private FragmentManager fragmentManager;
    private String currentFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reside);
        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = SystemUtils.getStatusHeight(this);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }

        setIconDrawable(mAll, MaterialDesignIconic.Icon.gmi_view_comfy);
        setIconDrawable(mFuli, MaterialDesignIconic.Icon.gmi_mood);
        setIconDrawable(mAndroid, MaterialDesignIconic.Icon.gmi_android);
        setIconDrawable(mIos, MaterialDesignIconic.Icon.gmi_apple);
        setIconDrawable(mVideo, MaterialDesignIconic.Icon.gmi_collection_video);
        setIconDrawable(mFront, MaterialDesignIconic.Icon.gmi_language_javascript);
        setIconDrawable(mResource, FontAwesome.Icon.faw_location_arrow);
        setIconDrawable(mApp, MaterialDesignIconic.Icon.gmi_apps);
        setIconDrawable(mAbout, MaterialDesignIconic.Icon.gmi_account);
        setIconDrawable(mTheme, MaterialDesignIconic.Icon.gmi_palette);
        setIconDrawable(mMore, MaterialDesignIconic.Icon.gmi_more);


        Picasso.with(this)
                .load(R.drawable.ic_avatar)
                .placeholder(new IconicsDrawable(this)
                        .icon(FoundationIcons.Icon.fou_photo)
                        .color(Color.GRAY)
                        .backgroundColor(Color.WHITE)
                        .roundedCornersDp(40)
                        .paddingDp(15)
                        .sizeDp(75))
                .transform(new CropCircleTransformation())
                .into(mAvatar);

        mDesc.setText("Just Do IT");

        if (PreUtils.getBoolean(this, "isFirst", true)) {
            mResideLayout.openPane();
            PreUtils.putBoolean(this, "isFirst", false);
        }
        mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_view_comfy).sizeDp(20));
        mTitle.setText("干货集中营");
        switchFragment("all");
    }

    private void setIconDrawable(TextView view, IIcon icon) {
        view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(this)
                        .icon(icon)
                        .color(Color.WHITE)
                        .sizeDp(16),
                null, null, null);
        view.setCompoundDrawablePadding(DimenUtils.dp2px(this, 10));
    }

    public void switchFragment(String name) {
        if (currentFragmentTag != null && currentFragmentTag.equals(name))
            return;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        Fragment currentFragment = fragmentManager.findFragmentByTag(currentFragmentTag);
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }

        Fragment foundFragment = fragmentManager.findFragmentByTag(name);

        if (foundFragment == null) {
            if (name.equals("all")){
                foundFragment = new GirlMainFragment();
            }else if (name.equals("福利")){
                foundFragment = new SettingFragment();
            }else {
                foundFragment = TestFragment.newInstance(name);
            }
        }

        if (foundFragment == null) {

        } else if (foundFragment.isAdded()) {
            ft.show(foundFragment);
        } else {
            ft.add(R.id.container, foundFragment, name);
        }
        ft.commit();
        currentFragmentTag = name;
    }

    @OnClick({R.id.avatar, R.id.all, R.id.fuli, R.id.android,
            R.id.ios, R.id.video, R.id.front,
            R.id.resource, R.id.about,
            R.id.app, R.id.theme, R.id.icon, R.id.more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                break;
            case R.id.all:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_view_comfy).sizeDp(20));
                mTitle.setText(R.string.app_name);
                switchFragment("all");
                break;
            case R.id.fuli:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_mood).sizeDp(20));
                mTitle.setText(R.string.fuli);
                switchFragment("福利");
                break;
            case R.id.android:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_android).sizeDp(20));
                mTitle.setText(R.string.android);
                switchFragment("Android");
                break;
            case R.id.ios:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_apple).sizeDp(20));
                mTitle.setText(R.string.ios);
                switchFragment("iOS");
                break;

            case R.id.video:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_collection_video).sizeDp(20));
                mTitle.setText(R.string.video);
                switchFragment("休息视频");
                break;
            case R.id.front:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_language_javascript).sizeDp(20));
                mTitle.setText(R.string.front);
                switchFragment("前端");
                break;
            case R.id.resource:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(FontAwesome.Icon.faw_location_arrow).sizeDp(20));
                mTitle.setText(R.string.resource);
                switchFragment("拓展资源");
                break;
            case R.id.app:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_apps).sizeDp(20));
                mTitle.setText(R.string.app);
                switchFragment("App");
                break;
            case R.id.more:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_more).sizeDp(20));
                mTitle.setText(R.string.more);
                switchFragment("瞎推荐");
                break;

            case R.id.about:
                new MaterialDialog.Builder(this)
                        .title(R.string.about)
                        .icon(new IconicsDrawable(this)
                                .color(ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
                                .icon(MaterialDesignIconic.Icon.gmi_account)
                                .sizeDp(20))
                        .content(R.string.about_me)
                        .positiveText(R.string.close)
                        .show();
                break;
            case R.id.theme:
                new ColorChooserDialog.Builder(this, R.string.theme)
                        .customColors(R.array.colors, null)
                        .doneButton(R.string.done)
                        .cancelButton(R.string.cancel)
                        .allowUserColorInput(false)
                        .allowUserColorInputAlpha(false)
                        .show();
                break;
            case R.id.icon:
                mResideLayout.openPane();
                break;
        }
    }

   @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        if (selectedColor == ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
            return;
        EventBus.getDefault().post(new SkinChangeEvent());

        if (selectedColor == ContextCompat.getColor(this,R.color.colorBluePrimary)) {
            setTheme(R.style.BlueTheme);
            PreUtils.setCurrentTheme(this, Theme.Blue);

        } else if (selectedColor == ContextCompat.getColor(this,R.color.colorRedPrimary)) {
            setTheme(R.style.RedTheme);
            PreUtils.setCurrentTheme(this, Theme.Red);

        } else if (selectedColor == ContextCompat.getColor(this,R.color.colorBrownPrimary)) {
            setTheme(R.style.BrownTheme);
            PreUtils.setCurrentTheme(this, Theme.Brown);

        } else if (selectedColor == ContextCompat.getColor(this,R.color.colorGreenPrimary)) {
            setTheme(R.style.GreenTheme);
            PreUtils.setCurrentTheme(this, Theme.Green);

        } else if (selectedColor == ContextCompat.getColor(this,R.color.colorPurplePrimary)) {
            setTheme(R.style.PurpleTheme);
            PreUtils.setCurrentTheme(this, Theme.Purple);

        } else if (selectedColor == ContextCompat.getColor(this,R.color.colorTealPrimary)) {
            setTheme(R.style.TealTheme);
            PreUtils.setCurrentTheme(this, Theme.Teal);

        } else if (selectedColor == ContextCompat.getColor(this,R.color.colorPinkPrimary)) {
            setTheme(R.style.PinkTheme);
            PreUtils.setCurrentTheme(this, Theme.Pink);

        } else if (selectedColor == ContextCompat.getColor(this,R.color.colorDeepPurplePrimary)) {
            setTheme(R.style.DeepPurpleTheme);
            PreUtils.setCurrentTheme(this, Theme.DeepPurple);

        } else if (selectedColor == ContextCompat.getColor(this,R.color.colorOrangePrimary)) {
            setTheme(R.style.OrangeTheme);
            PreUtils.setCurrentTheme(this, Theme.Orange);

        } else if (selectedColor == ContextCompat.getColor(this,R.color.colorIndigoPrimary)) {
            setTheme(R.style.IndigoTheme);
            PreUtils.setCurrentTheme(this, Theme.Indigo);

        } else if (selectedColor == ContextCompat.getColor(this,R.color.colorLightGreenPrimary)) {
            setTheme(R.style.LightGreenTheme);
            PreUtils.setCurrentTheme(this, Theme.LightGreen);

        } else if (selectedColor == ContextCompat.getColor(this,R.color.colorDeepOrangePrimary)) {
            setTheme(R.style.DeepOrangeTheme);
            PreUtils.setCurrentTheme(this, Theme.DeepOrange);

        } else if (selectedColor == ContextCompat.getColor(this,R.color.colorLimePrimary)) {
            setTheme(R.style.LimeTheme);
            PreUtils.setCurrentTheme(this, Theme.Lime);

        } else if (selectedColor == ContextCompat.getColor(this,R.color.colorBlueGreyPrimary)) {
            setTheme(R.style.BlueGreyTheme);
            PreUtils.setCurrentTheme(this, Theme.BlueGrey);

        } else if (selectedColor ==ContextCompat.getColor(this,R.color.colorCyanPrimary)) {
            setTheme(R.style.CyanTheme);
            PreUtils.setCurrentTheme(this, Theme.Cyan);

        }
        final View rootView = getWindow().getDecorView();
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache(true);

        final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        if (null != localBitmap && rootView instanceof ViewGroup) {
            final View tmpView = new View(getApplicationContext());
            tmpView.setBackgroundDrawable(new BitmapDrawable(getResources(), localBitmap));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) rootView).addView(tmpView, params);
            tmpView.animate().alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    ColorUiUtil.changeTheme(rootView, getTheme());
                    System.gc();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ((ViewGroup) rootView).removeView(tmpView);
                    localBitmap.recycle();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    }



    @Override
    public void onBackPressed() {
        if (mResideLayout.isOpen()) {
            mResideLayout.closePane();
        } else {
            super.onBackPressed();
        }
    }
}
