package com.senter.demo.uhf.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.senter.demo.uhf.Contants.UserMessage;
import com.senter.demo.uhf.R;

import java.util.List;

/**
 * Created by Lenovo on 2017/6/13.
 */

public class UserAdapter extends BaseAdapter {
    private List<UserMessage> list;
    private LayoutInflater inflater;

    public UserAdapter(Context context, List<UserMessage> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (list != null) {
            ret = list.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserMessage userMessage = (UserMessage) this.getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.useritem, null);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.userJob = (TextView) convertView.findViewById(R.id.user_job);
            viewHolder.userNum = (TextView) convertView.findViewById(R.id.user_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.userName.setText(userMessage.getUsername());
        viewHolder.userName.setTextSize(15);
        viewHolder.userJob.setText(userMessage.getUserjob());
        viewHolder.userJob.setTextSize(15);
        viewHolder.userNum.setText(userMessage.getUsernumber());
        viewHolder.userNum.setTextSize(15);

        return convertView;
    }

    public static class ViewHolder {
        public TextView userName;
        public TextView userJob;
        public TextView userNum;
    }
}
