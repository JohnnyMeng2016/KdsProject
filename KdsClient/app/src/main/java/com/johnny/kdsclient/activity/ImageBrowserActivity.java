package com.johnny.kdsclient.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.widget.PhotoInfo;
import com.johnny.kdsclient.widget.PhotoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/14
 */
public class ImageBrowserActivity extends BaseActivity {
    @BindView(R.id.vp_image_brower)
    ViewPager imageViewPager;
    @BindView(R.id.tv_image_index)
    TextView tvImageIndex;

    private PhotoView[] photoViewArray;
    private int currentIndex;
    String[] imgUrls;
    int position;
    PhotoInfo info;
    private boolean isFirst = true;

    @Override
    protected int layout() {
        return R.layout.activity_image_brower;
    }

    @Override
    protected void initDate() {
        imgUrls = getIntent().getStringArrayExtra("imgUrls");
        position = getIntent().getIntExtra("position", 0);
        info = getIntent().getParcelableExtra("info");
        photoViewArray = new PhotoView[imgUrls.length];
    }

    @Override
    protected void initView() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setBackEnable(false);
        tvImageIndex.setText("" + (position + 1) + "/" + imgUrls.length);
        imageViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgUrls.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView photoView = new PhotoView(ImageBrowserActivity.this);
                if (position == ImageBrowserActivity.this.position && isFirst) {
                    isFirst = false;
                    photoView.animaFrom(info);
                }
                photoView.setLayoutParams(new AbsListView.LayoutParams((int) (getResources().getDisplayMetrics().density * 100), (int) (getResources().getDisplayMetrics().density * 100)));
                photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Glide.with(ImageBrowserActivity.this).load(imgUrls[position]).into(photoView);
                photoView.enable();
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((PhotoView) v).animaTo(info, new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });
                    }
                });
                container.addView(photoView);
                photoViewArray[position] = photoView;
                return photoView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvImageIndex.setText("" + (position + 1) + "/" + imgUrls.length);
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imageViewPager.setCurrentItem(position);
    }

    @Override
    public void onBackPressed() {
        photoViewArray[currentIndex].animaTo(info, new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }
}
