package com.senter.demo.uhf.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.senter.demo.uhf.R;
import com.senter.demo.uhf.util.Goods;

import java.util.List;

/**
 * Created by Lenovo on 2017/5/5.
 */

public class TableAdapter extends BaseAdapter {
    private List<Goods> list;
    private LayoutInflater inflater;

    public TableAdapter(Context context, List<Goods> list) {
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
        Goods goods = (Goods) this.getItem(position);

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_getmessage, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.date = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.job = (TextView) convertView.findViewById(R.id.tv_job);
            viewHolder.jobId = (TextView) convertView.findViewById(R.id.tv_jobnumber);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(goods.getName());
        viewHolder.name.setTextSize(13);
        viewHolder.date.setText(goods.getDate());
        viewHolder.date.setTextSize(13);
        viewHolder.job.setText(goods.getJob());
        viewHolder.job.setTextSize(13);
        viewHolder.jobId.setText(goods.getJobId()+"");
        viewHolder.jobId.setTextSize(13);
        return convertView;
    }
    public static class ViewHolder{
        public TextView name;
        public TextView date;
        public TextView job;
        public TextView jobId;
    }
}
