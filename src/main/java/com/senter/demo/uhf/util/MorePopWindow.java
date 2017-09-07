package com.senter.demo.uhf.util;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.senter.demo.uhf.R;
import com.senter.demo.uhf.commonActivity.RecordsBoard;

import java.util.ArrayList;
import java.util.HashMap;

public class MorePopWindow extends PopupWindow {

    private Activity popuwindow;
    private final ArrayList<HashMap<String, String>> dataMerged = new ArrayList<HashMap<String, String>>();
    private View conentView;
    private SimpleAdapter adapterMergedData;
    private RecordsBoard recordsBoard;

    private TextView tvCount;

    public MorePopWindow(final Activity context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.more_pop_layout, null);

        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 3);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);
        LinearLayout emptyLayout = (LinearLayout) conentView
                .findViewById(R.id.ll_watse);
        LinearLayout seemessageLayout = (LinearLayout) conentView
                .findViewById(R.id.ll_seemessage);
        LinearLayout storageLayout = (LinearLayout) conentView
                .findViewById(R.id.ll_storage);
        emptyLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
        seemessageLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                MorePopWindow.this.dismiss();
            }
        });
        storageLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                MorePopWindow.this.dismiss();
            }
        });

    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            this.dismiss();
        }
    }
}
