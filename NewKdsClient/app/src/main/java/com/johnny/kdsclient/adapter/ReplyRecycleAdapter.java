package com.johnny.kdsclient.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.activity.ImageBrowserActivity;
import com.johnny.kdsclient.activity.UserDetailActivity;
import com.johnny.kdsclient.bean.ContentParsedBean;
import com.johnny.kdsclient.bean.Quote;
import com.johnny.kdsclient.bean.Reply;
import com.johnny.kdsclient.bean.Topic;
import com.johnny.kdsclient.utils.CommonUtils;
import com.johnny.kdsclient.utils.StringUtils;
import com.johnny.kdsclient.widget.PhotoInfo;
import com.johnny.kdsclient.widget.PhotoView;

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
    private int gridImgsLineHeight;
    private Topic topic;

    public ReplyRecycleAdapter(Context context, Topic topic) {
        this.context = context;
        this.datas = new ArrayList<Reply>();
        this.layoutInflater = LayoutInflater.from(context);
        this.gridImgsLineHeight = CommonUtils.dp2Px(context, 100);
        this.topic = topic;
    }

    public List<Reply> getDatas() {
        return datas;
    }

    public void setDatas(List<Reply> datas) {
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
            View view = layoutInflater.inflate(R.layout.item_reply, parent, false);
            ReplyRecycleHolder replyRecycleHolder = new ReplyRecycleHolder(view);
            return replyRecycleHolder;
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReplyRecycleHolder) {
            ReplyRecycleHolder replyRecycleHolder = (ReplyRecycleHolder) holder;
            final Reply reply = datas.get(position);
            final ImageView ivAvatar = replyRecycleHolder.ivAvatar;
            Glide.with(context).load(reply.getUserdata().getPhoto()).into(ivAvatar);
            replyRecycleHolder.ivAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserDetailActivity.class);
                    intent.putExtra(UserDetailActivity.EXTRA_REPLY, reply);
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity) context,
                                    ivAvatar, UserDetailActivity.NAME_IMG_AVATAR);
                    ActivityCompat.startActivity((Activity) context, intent, options.toBundle());
                }
            });
            if ("男".equals(reply.getUserdata().getSex())) {
                replyRecycleHolder.ivSex.setVisibility(View.VISIBLE);
                replyRecycleHolder.ivSex.setImageResource(R.mipmap.ic_profile_male);
            } else if ("女".equals(reply.getUserdata().getSex())) {
                replyRecycleHolder.ivSex.setVisibility(View.VISIBLE);
                replyRecycleHolder.ivSex.setImageResource(R.mipmap.ic_profile_female);
            } else {
                replyRecycleHolder.ivSex.setVisibility(View.GONE);
            }
            StringBuffer userNameShow = new StringBuffer();
            userNameShow.append(reply.getNickName());
            userNameShow.append("(");
            userNameShow.append(reply.getUserName());
            userNameShow.append(")");
            replyRecycleHolder.tvUserName.setText(userNameShow.toString());
            if (reply.getFloor() == 1) {
                replyRecycleHolder.tvTitle.setText("主题：" + topic.getTitle());
                replyRecycleHolder.tvTitle.setVisibility(View.VISIBLE);
                replyRecycleHolder.tvFloor.setText("#楼主");
            } else {
                replyRecycleHolder.tvTitle.setVisibility(View.GONE);
                replyRecycleHolder.tvFloor.setText("#" + reply.getFloor() + "楼");
            }
            replyRecycleHolder.tvHpScore.setText(String.valueOf(reply.getUserdata().getHp()));
            replyRecycleHolder.tvPpScore.setText(String.valueOf(reply.getUserdata().getPp()));
            replyRecycleHolder.tvDateTime.setText(reply.getPostTime());

            /**
             * 以下一段代码很蛋疼，原有是API返回数据极其怪异，回复的引用可能出现在两个字段里(内嵌在MessageShow，quote_list)
             */
            ContentParsedBean contentBean = StringUtils.parseReplyContent(reply.getMessageShow());
            if (contentBean.getRefrence() != null) {//直接从MessageShow中解析出引用内容
                replyRecycleHolder.refrenceLayout.setVisibility(View.VISIBLE);
                SpannableString spannableString = StringUtils.getItemContent(context,
                        replyRecycleHolder.tvRefContent, contentBean.getRefrence());
                replyRecycleHolder.tvRefContent.setText(spannableString);
                setImage(contentBean.getRefrenceImgs(), replyRecycleHolder.refImgLayout);
            } else if (reply.getQuote_list() != null && reply.getQuote_list().size() != 0) {//加载引用部分
                replyRecycleHolder.refrenceLayout.setVisibility(View.VISIBLE);
                StringBuffer refrenceSb = new StringBuffer();
                for (Quote quote : reply.getQuote_list()) {
                    refrenceSb.append(quote.getMessage());
                    refrenceSb.append("<br/>");
                    refrenceSb.append("引用自" + quote.getFloor() + "楼:");
                    refrenceSb.append(quote.getNickName() + "(" + quote.getUserName() + ")");
                    refrenceSb.append("<br/>");
                }
                ContentParsedBean content = StringUtils.parseReplyContent(refrenceSb.toString());
                SpannableString spannableString = StringUtils.getItemContent(context,
                        replyRecycleHolder.tvRefContent, content.getContent());
                replyRecycleHolder.tvRefContent.setText(spannableString);
                setImage(content.getContentImgs(), replyRecycleHolder.refImgLayout);
            } else {//无引用
                replyRecycleHolder.refrenceLayout.setVisibility(View.GONE);
            }

            SpannableString spannableString = StringUtils.getItemContent(context,
                    replyRecycleHolder.tvContent, contentBean.getContent());
            replyRecycleHolder.tvContent.setText(spannableString);
            setImage(contentBean.getContentImgs(), replyRecycleHolder.imgLayout);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    private void setImage(List<String> imgs, FrameLayout imgLayout) {
        if (imgs.size() > 0) {
            imgLayout.setVisibility(View.VISIBLE);
            GridView gvImages = (GridView) imgLayout.getChildAt(0);
            final PhotoView ivImage = (PhotoView) imgLayout.getChildAt(1);
            if (imgs.size() == 1) {
                ivImage.setVisibility(View.VISIBLE);
                gvImages.setVisibility(View.GONE);

                Glide.with(context)
                        .load(imgs.get(0))
                        .asBitmap()
                        .placeholder(R.mipmap.image_placeholder)
                        .dontAnimate()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                ivImage.setImageBitmap(resource);
                            }
                        });

                final String imgUrl = imgs.get(0);
                ivImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoInfo photoInfo = ((PhotoView) v).getInfo();
                        Intent intent = new Intent(context, ImageBrowserActivity.class);
                        intent.putExtra("imgUrls", new String[]{imgUrl});
                        intent.putExtra("position", 0);
                        intent.putExtra("infos", new PhotoInfo[]{photoInfo});
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                    }
                });
            } else {
                ivImage.setVisibility(View.GONE);
                gvImages.setVisibility(View.VISIBLE);
                float lineF = (float) (imgs.size()) / 3.0f;
                int line = (int) (Math.ceil(lineF));
                gvImages.getLayoutParams().height = line * gridImgsLineHeight;
                GridImgAdapter gridImgAdapter = new GridImgAdapter(context, imgs);
                gvImages.setAdapter(gridImgAdapter);
            }
        } else {
            imgLayout.setVisibility(View.GONE);
        }
    }


    public class ReplyRecycleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_cardview)
        CardView cardView;
        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @BindView(R.id.iv_sex)
        ImageView ivSex;
        @BindView(R.id.tv_username)
        TextView tvUserName;
        @BindView(R.id.tv_floor)
        TextView tvFloor;
        @BindView(R.id.tv_hp_score)
        TextView tvHpScore;
        @BindView(R.id.tv_pp_score)
        TextView tvPpScore;
        @BindView(R.id.tv_datetime)
        TextView tvDateTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.include_img_layout)
        FrameLayout imgLayout;
        @BindView(R.id.include_refrence_layout)
        LinearLayout refrenceLayout;
        @BindView(R.id.tv_refrence_content)
        TextView tvRefContent;
        @BindView(R.id.include_refrence_image)
        FrameLayout refImgLayout;

        public ReplyRecycleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
