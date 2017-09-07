package com.senter.demo.uhf.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ViewPagerIndicator extends LinearLayout {

	private int mTriangleWidth;
	private static final float RADIO_TRANGLE_WIDTH = 1 / 6F;
	private int mInitTransiationX;
	private int mTransiationX;
	private Context mContext;
	private ViewPager mViewPager;

	public ViewPagerIndicator(Context context) {
		this(context, null);
	}

	public ViewPagerIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mTriangleWidth = (int) (w / 3 * RADIO_TRANGLE_WIDTH);
		mInitTransiationX = w / 3 / 2 - mTriangleWidth / 2;
	}

	public void scroll(int position, float positionOffset) {
		int tabWidth = getWidth() / 3;
		mTransiationX = (int) (tabWidth * (position + positionOffset));
		invalidate();
	}

	private void setTextFocuse(int position) {

		setTextOutFocuse();

		View tabView = getChildAt(position);

		if (tabView instanceof RelativeLayout) {
			RelativeLayout tabImageViewRelearLayout = (RelativeLayout) tabView;

			ImageView tabImageView = (ImageView) tabImageViewRelearLayout
					.getChildAt(0);

			if (tabImageViewRelearLayout.getId() == mContext.getResources()
					.getIdentifier("rl_home", "id", mContext.getPackageName())) {
				tabImageView.setImageResource(mContext.getResources()
						.getIdentifier("home_icon_press", "drawable",
								mContext.getPackageName()));
			} else if (tabImageViewRelearLayout.getId() == mContext
					.getResources().getIdentifier("rl_founction", "id",
							mContext.getPackageName())) {
				tabImageView.setImageResource(mContext.getResources()
						.getIdentifier("founction_icon_press", "drawable",
								mContext.getPackageName()));
			} else if (tabImageViewRelearLayout.getId() == mContext
					.getResources().getIdentifier("settings", "id",
							mContext.getPackageName())) {
				tabImageView.setImageResource(mContext.getResources()
						.getIdentifier("setting_press", "drawable",
								mContext.getPackageName()));

			}
		}
	}

	private void setTextOutFocuse() {

		for (int i = 0; i < getChildCount(); i++) {
			View tabView = getChildAt(i);

			if (tabView instanceof RelativeLayout) {
				RelativeLayout tabImageViewRelearLayout = (RelativeLayout) tabView;

				ImageView tabImageView = (ImageView) tabImageViewRelearLayout
						.getChildAt(0);

				if (tabImageViewRelearLayout.getId() == mContext.getResources()
						.getIdentifier("rl_home", "id",
								mContext.getPackageName())) {
					tabImageView.setImageResource(mContext.getResources()
							.getIdentifier("home_icon", "drawable",
									mContext.getPackageName()));
				} else if (tabImageViewRelearLayout.getId() == mContext
						.getResources().getIdentifier("rl_founction", "id",
								mContext.getPackageName())) {
					tabImageView.setImageResource(mContext.getResources()
							.getIdentifier("founction_icon", "drawable",
									mContext.getPackageName()));
				} else if (tabImageViewRelearLayout.getId() == mContext
						.getResources().getIdentifier("settings", "id",
								mContext.getPackageName())) {
					tabImageView.setImageResource(mContext.getResources()
							.getIdentifier("settings", "drawable",
									mContext.getPackageName()));
				}
			}
		}

	}

	public void setViewPager(ViewPager viewPager, final int position) {
		mViewPager = viewPager;
		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {
						scroll(position, positionOffset);
					}

					@Override
					public void onPageSelected(int position) {
						setTextFocuse(position);
					}

					@Override
					public void onPageScrollStateChanged(int state) {
					}
				});

		mViewPager.setCurrentItem(position);
		setTabOnclickListemer();
		setTextFocuse(position);
	}

	private void setTabOnclickListemer() {

		for (int i = 0; i < getChildCount(); i++) {
			final int j = i;
			View childView = getChildAt(i);

			if (childView instanceof RelativeLayout) {
				RelativeLayout relativeLayoutChildView = (RelativeLayout) childView;
				ImageView tabImageView = (ImageView) relativeLayoutChildView
						.getChildAt(0);
				tabImageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mViewPager.setCurrentItem(j);
					}
				});
			}

			childView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mViewPager.setCurrentItem(j);
				}
			});

		}

	}
}
