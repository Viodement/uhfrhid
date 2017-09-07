package com.senter.demo.uhf.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.senter.demo.uhf.Contants.ProductMessage;
import com.senter.demo.uhf.R;

import java.util.List;

/**
 * Created by Lenovo on 2017/6/13.
 */

public class ProductAdapter extends BaseAdapter {
    private List<ProductMessage> list;
    private LayoutInflater inflater;

    public ProductAdapter(Context context, List<ProductMessage> list) {
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
        ProductMessage productMessage = (ProductMessage) this.getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.useritem, null);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.productStatus = (TextView) convertView.findViewById(R.id.user_job);
            viewHolder.productMore = (TextView) convertView.findViewById(R.id.user_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.productName.setText(productMessage.getProductName());
        viewHolder.productName.setTextSize(15);
        viewHolder.productStatus.setText(productMessage.getProductStatus());
        viewHolder.productStatus.setTextSize(15);
        viewHolder.productMore.setText(productMessage.getProductMore());
        viewHolder.productMore.setTextSize(15);

        return convertView;
    }

    public static class ViewHolder {
        public TextView productName;
        public TextView productStatus;
        public TextView productMore;
    }
}
