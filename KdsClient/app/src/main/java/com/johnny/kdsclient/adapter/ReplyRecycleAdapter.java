package com.johnny.kdsclient.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.bean.Reply;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/10
 */
public class ReplyRecycleAdapter extends RecyclerView.Adapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Context context;
    private List<Reply> datas;
    private LayoutInflater layoutInflater;
    private FooterViewHolder footerViewHolder;

    public ReplyRecycleAdapter(Context context) {
        this.context = context;
        this.datas = new ArrayList<Reply>();
        this.layoutInflater = LayoutInflater.from(context);
    }

    public List<Reply> getDatas() {
        return datas;
    }

    public void setDatas(List<Reply> datas) {
        this.datas = datas;
    }

    public void setFooterViewType(boolean isEnd){
        if(isEnd){
            footerViewHolder.progressBar.setVisibility(View.GONE);
            footerViewHolder.textView.setVisibility(View.VISIBLE);
        }else{
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
            View view = layoutInflater.inflate(R.layout.item_reply, parent, false);
            ReplyRecycleHolder replyRecycleHolder = new ReplyRecycleHolder(view);
            return replyRecycleHolder;
        }
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ReplyRecycleHolder){
            ReplyRecycleHolder replyRecycleHolder = (ReplyRecycleHolder) holder;
            Reply reply = datas.get(position);
            Glide.with(context).load(reply.getUserAvatar()).into(replyRecycleHolder.ivAvatar);
            replyRecycleHolder.tvUserName.setText(reply.getNickName());
            replyRecycleHolder.tvDateTime.setText(reply.getTime());
            replyRecycleHolder.tvContent.setText(Html.fromHtml(reply.getContent()));
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    public class ReplyRecycleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_cardview)
        CardView cardView;
        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @BindView(R.id.tv_username)
        TextView tvUserName;
        @BindView(R.id.tv_datetime)
        TextView tvDateTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.include_img_layout)
        FrameLayout imgLayout;
        @BindView(R.id.gv_images)
        GridView gvImages;
        @BindView(R.id.iv_image)
        ImageView ivImage;

        public ReplyRecycleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
