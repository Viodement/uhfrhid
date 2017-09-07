package com.senter.demo.uhf.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Lenovo on 2017/4/1.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	private String[] titles = { "盘点", "写入", "读取", "设置" };

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	public void setFragments(List<Fragment> fragments) {
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments != null ? fragments.size() : 0;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}
}
