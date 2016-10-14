package com.johnny.kdsclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.johnny.kdsclient.R;

import java.util.List;

/**
 * 项目名称：KdsClient
 * 类描述：多图适配器
 * 创建人：孟忠明
 * 创建时间：2016/10/14
 */
public class GridImgAdapter extends BaseAdapter {

    private Context context;
    private List<String> imgs;
    private LayoutInflater layoutInflater;

    public GridImgAdapter(Context context, List<String> imgs) {
        this.context = context;
        this.imgs = imgs;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Object getItem(int position) {
        return imgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_grid_image, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GridView gv = (GridView) parent;
        int horizontalSpacing = gv.getHorizontalSpacing();
        int numColumns = gv.getNumColumns();
        int itemWidth = (gv.getWidth() - (numColumns - 1) * horizontalSpacing
                - gv.getPaddingLeft() - gv.getPaddingRight()) / numColumns;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itemWidth, itemWidth);
        holder.iv_image.setLayoutParams(params);

        Glide.with(context).load(imgs.get(position)).into(holder.iv_image);

        return convertView;
    }

    public static class ViewHolder {
        public ImageView iv_image;
    }
}
