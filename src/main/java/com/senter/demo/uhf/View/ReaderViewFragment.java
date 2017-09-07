package com.senter.demo.uhf.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.senter.demo.uhf.R;
import com.senter.demo.uhf.modelD2.Activity0Inventory;

/**
 * Created by Lenovo on 2017/4/1.
 */

public class ReaderViewFragment extends Fragment {
    private ImageView invertory;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getActivity().getResources().
                getIdentifier("userviewlayout", "layout",
                        getActivity().getPackageName()), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getActivity());
        initlistener();
    }


    private void initlistener() {
        invertory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), Activity0Inventory.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private void initView(FragmentActivity fragmentActivity) {
        invertory = (ImageView) fragmentActivity.findViewById(R.id.invertory);
    }

    public static ReaderViewFragment newInstance() {
        return new ReaderViewFragment();
    }
}
