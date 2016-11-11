package com.johnny.kdsclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.activity.TopicDetailActivity;
import com.johnny.kdsclient.bean.Topic;
import com.johnny.kdsclient.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/18
 */
public class ImageRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Context context;
    private List<Topic> datas;
    private LayoutInflater layoutInflater;
    private FooterViewHolder footerViewHolder;
    private List<Integer> heights;

    public ImageRecycleAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.datas = new ArrayList<>();
        this.heights = new ArrayList<>();
    }

    public List<Topic> getDatas() {
        return datas;
    }

    public void setDatas(List<Topic> datas) {
        this.datas = datas;
    }

    public void setFooterViewType(boolean isEnd) {
        if (isEnd) {
            footerViewHolder.progressBar.setVisibility(View.GONE);
            footerViewHolder.textView.setVisibility(View.VISIBLE);
        } else {
            footerViewHolder.progressBar.setVisibility(View.VISIBLE);
            footerViewHolder.textView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = layoutInflater.inflate(R.layout.item_foot_loading, parent, false);
            footerViewHolder = new FooterViewHolder(view);
            return footerViewHolder;
        } else {
            View view = layoutInflater.inflate(R.layout.item_image, parent, false);
            ImageRecycleHolder topicRecycleHolder = new ImageRecycleHolder(view);
            return topicRecycleHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageRecycleHolder) {
            final ImageRecycleHolder imageRecycleHolder = ((ImageRecycleHolder) holder);

            if(heights.size()<=position){
                heights.add(CommonUtils.dp2Px(context,80)+(int)(Math.random()*300));
            }
            imageRecycleHolder.imageView.getLayoutParams().height = heights.get(position);

            final Topic topic = datas.get(position);
            String imgUrl = topic.getPreview().replace("128w","256w");
            imgUrl = imgUrl.replace("128h","256h");
            Glide.with(context).load(imgUrl).into(imageRecycleHolder.imageView);

            imageRecycleHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("topic",topic);
                    intent.setClass(context, TopicDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    class ImageRecycleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_cardview)
        CardView cardView;
        @BindView(R.id.iv_image)
        ImageView imageView;

        public ImageRecycleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
