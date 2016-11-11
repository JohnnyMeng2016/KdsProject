package com.johnny.kdsclient.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.activity.ImageBrowserActivity;
import com.johnny.kdsclient.widget.PhotoInfo;
import com.johnny.kdsclient.widget.PhotoView;

import java.util.ArrayList;
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
    private String[] imgUrls;
    private PhotoInfo[] photoInfos;
    private PhotoView[] photoViews;
    private LayoutInflater layoutInflater;

    public GridImgAdapter(Context context, List<String> imgs) {
        this.context = context;
        this.imgs = imgs;
        this.layoutInflater = LayoutInflater.from(context);
        imgUrls = new String[imgs.size()];
        photoInfos = new PhotoInfo[imgs.size()];
        photoViews = new PhotoView[imgs.size()];
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_grid_image, null);
            holder.iv_image = (PhotoView) convertView.findViewById(R.id.iv_image);
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
        Glide.with(context).load(imgs.get(position)).placeholder(R.mipmap.image_placeholder).dontAnimate().into(holder.iv_image);

        photoViews[position] = holder.iv_image;
        imgUrls[position] = imgs.get(position);
        holder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup viewGroup = (ViewGroup) parent.getChildAt(0);
                PhotoView photoView = (PhotoView) viewGroup.getChildAt(0);
                photoViews[0] = photoView;
                for(int i=0;i<photoViews.length;i++){
                    photoInfos[i] = photoViews[i].getInfo();
                }
                Intent intent = new Intent(context, ImageBrowserActivity.class);
                intent.putExtra("imgUrls", imgUrls);
                intent.putExtra("position", position);
                intent.putExtra("infos",photoInfos);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(0, 0);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        public PhotoView iv_image;
    }
}
