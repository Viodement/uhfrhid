package com.senter.demo.uhf.commonActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.senter.demo.common.misc.ActivityHelper;
import com.senter.demo.uhf.R;
import com.senter.demo.uhf.View.HomeViewFragment;
import com.senter.demo.uhf.View.ReaderViewFragment;
import com.senter.demo.uhf.View.SettingViewFragment;
import com.senter.demo.uhf.View.ViewPagerIndicator;
import com.senter.demo.uhf.modelD2.ConfigurationSettingsOfModelD2;
import com.senter.demo.uhf.util.App;
import com.senter.demo.uhf.util.ExceptionForToast;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends FragmentActivity {
    //定义ViewPager
    private ViewPager mViewPager;
    private ViewPagerIndicator mViewPagerIndicator;
    //定义ImageView
    private ImageView mImageView;

    private long exitTime = 0;

    private final ConfigurationSettingsOfModelD2 mConfigurationSettingsOfModelD2
            = ConfigurationSettingsOfModelD2.getInstance();
    //定义协议
    private ConfigurationSettingsOfModelD2.Protocol mCurrentProtocolOfModelD2 = null;

    private ActivityHelper<ViewPagerActivity> ah = new ActivityHelper<ViewPagerActivity>(this);
    //定义List存放fragment
    List<Fragment> fragmentList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            App.uhfInit();
        } catch (ExceptionForToast e) {
            e.printStackTrace();
            App.uhfClear();
            App.appCfgSaveModelClear();
            ah.showToastShort(e.getMessage());
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onCreateInitViews();
    }

    private void onCreateInitViews() {
        new Views();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode == 2){
            // 在这设置选中你要显示的fragment

        }
    }

    private class Views {
        public Views() {
            if ((mConfigurationSettingsOfModelD2.usingProtocol() == ConfigurationSettingsOfModelD2.Protocol.I8k6c)
                    && (mCurrentProtocolOfModelD2 == ConfigurationSettingsOfModelD2.Protocol.I8k6c)) {
                return;
            } else {
                //through down
            }
            //加载布局
            setContentView(R.layout.viewpagerlayout);
            initView();
            initDates();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_vpcontent);
        mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.vp_vpindacator);
        mImageView = (ImageView) findViewById(R.id.iv_title);
    }

    /**
     * 初始化监听
     */
    private void initDates() {
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // Fragment
        HomeViewFragment mViewPagerFragment_1 = HomeViewFragment.newInstance();
        ReaderViewFragment mViewPagerFragment_2 = ReaderViewFragment
                .newInstance();
        SettingViewFragment mViewPagerFragment_3 = SettingViewFragment
                .newInstance();
        fragmentList.add(mViewPagerFragment_1);
        fragmentList.add(mViewPagerFragment_2);
        fragmentList.add(mViewPagerFragment_3);

        FragmentPagerAdapter mFragmentAdapter = new FragmentPagerAdapter(
                getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPagerIndicator.setViewPager(mViewPager, 0);
    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode==KeyEvent.KEYCODE_BACK&& event.getAction()==KeyEvent.ACTION_DOWN){
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                Intent backHome = new Intent(Intent.ACTION_MAIN);
//                backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                backHome.addCategory(Intent.CATEGORY_HOME);
//                startActivity(backHome);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
