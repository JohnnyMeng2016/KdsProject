package com.johnny.kdsclient.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.MyDbHelper;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.UserData;
import com.johnny.kdsclient.adapter.EmotionGvAdapter;
import com.johnny.kdsclient.adapter.EmotionPagerAdapter;
import com.johnny.kdsclient.adapter.WriteTopicGridImgsAdapter;
import com.johnny.kdsclient.api.ApiHelper;
import com.johnny.kdsclient.api.SimpleResponseListener;
import com.johnny.kdsclient.bean.DraftTopic;
import com.johnny.kdsclient.bean.SendTopicRequest;
import com.johnny.kdsclient.utils.CommonUtils;
import com.johnny.kdsclient.utils.EmotionUtils;
import com.johnny.kdsclient.utils.ImageUtils;
import com.johnny.kdsclient.utils.StringUtils;
import com.johnny.kdsclient.utils.ThemeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名称：KdsClient
 * 类描述：发帖界面
 * 创建人：孟忠明
 * 创建时间：2016/10/31
 */
public class WriteTopicActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_write_title)
    EditText etWriteTitle;
    @BindView(R.id.et_write_content)
    EditText etWriteContent;
    @BindView(R.id.gv_write_topic)
    GridView gvWriteTopicImgs;
    @BindView(R.id.ll_bottom_tab)
    LinearLayout llBottom;
    @BindView(R.id.iv_takephoto)
    ImageView ivTakePhoto;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_image_library)
    ImageView ivImageLibrary;
    @BindView(R.id.iv_emoji)
    ImageView ivEmoji;
    @BindView(R.id.iv_voice)
    ImageView ivVoice;
    @BindView(R.id.ll_emotion_dashboard)
    LinearLayout llEmoji;
    @BindView(R.id.vp_emotion_dashboard)
    ViewPager vpEmoji;

    private ProgressDialog progressDialog;
    private WriteTopicGridImgsAdapter writeTopicGridImgsAdapter;
    private EmotionPagerAdapter emotionPagerGvAdapter;
    private List<Uri> imgUris = new ArrayList<Uri>();
    private List<String> imgAttachs = new ArrayList<String>();
    private DraftTopic draftTopic;
    private String userId;

    @Override
    protected void configTheme() {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.BaseAppTheme_NoActionBar, R.style.BaseAppThemeDark_NoActionBar);
    }

    @Override
    protected int layout() {
        return R.layout.activity_write_topic;
    }

    @Override
    protected void initDate() {
        draftTopic = (DraftTopic) getIntent().getParcelableExtra("Draft");
        initEmotion();
        userId = UserData.getInstance().getUserInfo().getUserId();
    }

    @Override
    protected void initView() {
        setBackEnable(false);
        toolbar.setTitle("发帖");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WriteTopicActivity.this);
                builder.setMessage("是否把当前内容保存至草稿?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveTopic();
                    }
                }).show();
            }
        });
        progressDialog = new ProgressDialog(this);
        writeTopicGridImgsAdapter = new WriteTopicGridImgsAdapter(this, imgUris, gvWriteTopicImgs);
        gvWriteTopicImgs.setAdapter(writeTopicGridImgsAdapter);
        gvWriteTopicImgs.setOnItemClickListener(this);

        etWriteTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llBottom.setVisibility(View.GONE);
                llEmoji.setVisibility(View.GONE);
                ivEmoji.setImageResource(R.drawable.ic_tag_face_selector);
            }
        });
        etWriteTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llBottom.setVisibility(View.GONE);
                    llEmoji.setVisibility(View.GONE);
                    ivEmoji.setImageResource(R.drawable.ic_tag_face_selector);
                }
            }
        });
        etWriteContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llBottom.setVisibility(View.VISIBLE);
                if (llEmoji.getVisibility() == View.VISIBLE) {
                    llEmoji.setVisibility(View.GONE);
                    ivEmoji.setImageResource(R.drawable.ic_tag_face_selector);
                }
            }
        });
        etWriteContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llBottom.setVisibility(View.VISIBLE);
                    if (llEmoji.getVisibility() == View.VISIBLE) {
                        llEmoji.setVisibility(View.GONE);
                        ivEmoji.setImageResource(R.drawable.ic_tag_face_selector);
                    }
                }
            }
        });

        if (null != draftTopic) {
            etWriteTitle.setText(draftTopic.getTitle());
            etWriteContent.setText(draftTopic.getContent());
            if (null != draftTopic.getImgUris() && draftTopic.getImgUris().size() > 0) {
                imgUris = draftTopic.getImgUris();
                writeTopicGridImgsAdapter.setDatas(imgUris);
                writeTopicGridImgsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write_topic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_send) {//发送
            if ("".equals(etWriteTitle.getText().toString())) {
                Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
                return true;
            }
            if ("".equals(etWriteContent.getText().toString()) && imgUris.size() == 0) {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                return true;
            }
            progressDialog.setMessage("发送中...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            //上传图片
            if (imgUris.size() > 0) {
                imgAttachs.clear();
                final int imgCount = imgUris.size();
                for (Uri imgUri : imgUris) {
                    File file = ImageUtils.uri2file(this, imgUri);
                    ApiHelper.getInstance().uploadPicture(userId, file, new SimpleResponseListener<String>() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(WriteTopicActivity.this, "发送失败，请再次尝试", Toast.LENGTH_SHORT)
                                    .show();
                        }

                        @Override
                        public void onResponse(String response) {
                            imgAttachs.add(response);
                            if (imgCount == imgAttachs.size()) {
                                sendTopic();
                            }
                        }
                    });
                }
            } else {
                sendTopic();
            }
            return true;
        } else if (id == R.id.action_save) {//保存至草稿箱
            saveTopic();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.iv_takephoto, R.id.iv_image, R.id.iv_image_library, R.id.iv_emoji, R.id.iv_voice,
            R.id.rb_emotion_dashboard_emoji, R.id.rb_emotion_dashboard_spacial})
    protected void onAction(View view) {
        switch (view.getId()) {
            case R.id.iv_takephoto:
                ImageUtils.pickImageFromCamera(this);
                break;
            case R.id.iv_image:
                ImageUtils.pickImageFromAlbum(this);
                break;
            case R.id.iv_image_library:
                break;
            case R.id.iv_emoji:
                if (llEmoji.getVisibility() == View.VISIBLE) {
                    llEmoji.setVisibility(View.GONE);
                    ivEmoji.setImageResource(R.drawable.ic_tag_face_selector);
                    CommonUtils.openKeybord(etWriteContent, this);
                } else {
                    CommonUtils.closeKeybord(etWriteContent, this);
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            llEmoji.setVisibility(View.VISIBLE);
                            ivEmoji.setImageResource(R.drawable.ic_keyboard_selector);
                        }
                    }, 200);

                }
                break;
            case R.id.iv_voice:
                break;
            case R.id.rb_emotion_dashboard_emoji:
                initEmotion();
                break;
            case R.id.rb_emotion_dashboard_spacial:
                initSpecialEmotion();
                break;
        }
    }

    /**
     * 初始化表情面板内容
     */
    private void initEmotion() {
        int screenWidth = CommonUtils.getScreenWidthPixels(this);
        int spacing = CommonUtils.dp2Px(this, 8);

        int itemWidth = (screenWidth - spacing * 8) / 7;
        int gvHeight = itemWidth * 3 + spacing * 4;

        List<GridView> gvs = new ArrayList<GridView>();
        List<Integer> emotionCodes = new ArrayList<Integer>();

        for (int emojiCode : EmotionUtils.emojiMap.keySet()) {
            if (emojiCode >= 56) {
                break;
            }
            emotionCodes.add(emojiCode);

            if (emotionCodes.size() == 21) {
                GridView gv = createEmotionGridView(emotionCodes, 7, screenWidth, spacing, itemWidth, gvHeight);
                gvs.add(gv);

                emotionCodes = new ArrayList<Integer>();
            }
        }

        if (emotionCodes.size() > 0) {
            GridView gv = createEmotionGridView(emotionCodes, 7, screenWidth, spacing, itemWidth, gvHeight);
            gvs.add(gv);
        }

        emotionPagerGvAdapter = new EmotionPagerAdapter(gvs);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
        vpEmoji.setLayoutParams(params);
        vpEmoji.setAdapter(emotionPagerGvAdapter);
    }

    /**
     * 创建显示表情的GridView
     */
    private GridView createEmotionGridView(List<Integer> emotionNames, int columnsNum, int gvWidth,
                                           int padding, int itemWidth, int gvHeight) {
        GridView gv = new GridView(this);
        gv.setBackgroundResource(R.color.bg_gray);
        gv.setSelector(R.color.transparent);
        gv.setNumColumns(columnsNum);
        gv.setPadding(padding, padding, padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gvWidth, gvHeight);
        gv.setLayoutParams(params);

        EmotionGvAdapter adapter = new EmotionGvAdapter(this, emotionNames, itemWidth);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(this);

        return gv;
    }

    /**
     * 初始化表情面板内容
     */
    private void initSpecialEmotion() {
        int screenWidth = CommonUtils.getScreenWidthPixels(this);
        int spacing = CommonUtils.dp2Px(this, 4);

        int itemWidth = (screenWidth - spacing * 8) / 5;
        int itemHeight = (screenWidth - spacing * 8) / 7;
        int gvHeight = itemHeight * 3 + spacing * 4;

        List<GridView> gvs = new ArrayList<GridView>();
        List<Integer> emotionCodes = new ArrayList<Integer>();

        for (int emojiCode : EmotionUtils.emojiMap.keySet()) {
            if (emojiCode < 56) {
                continue;
            }
            emotionCodes.add(emojiCode);

            if (emotionCodes.size() == 10) {
                GridView gv = createEmotionGridView(emotionCodes, 5, screenWidth, spacing, itemWidth, gvHeight);
                gvs.add(gv);

                emotionCodes = new ArrayList<Integer>();
            }
        }

        if (emotionCodes.size() > 0) {
            GridView gv = createEmotionGridView(emotionCodes, 5, screenWidth, spacing, itemWidth, gvHeight);
            gvs.add(gv);
        }

        emotionPagerGvAdapter = new EmotionPagerAdapter(gvs);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
        vpEmoji.setLayoutParams(params);
        vpEmoji.setAdapter(emotionPagerGvAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object itemAdapter = parent.getAdapter();
        if (itemAdapter instanceof WriteTopicGridImgsAdapter) {
            WriteTopicGridImgsAdapter imgsAdapter = (WriteTopicGridImgsAdapter) itemAdapter;
            if (position == imgsAdapter.getCount() - 1) {
                ImageUtils.showImagePickDialog(this);
            }
        } else if (itemAdapter instanceof EmotionGvAdapter) {
            EmotionGvAdapter emotionAdapter = (EmotionGvAdapter) itemAdapter;
            int emotionCode = emotionAdapter.getItem(position);
            String emotionName = "{emoji" + emotionCode + "}";
            int curPosition = etWriteContent.getSelectionStart();
            StringBuilder sb = new StringBuilder(etWriteContent.getText().toString());
            sb.insert(curPosition, emotionName);
            SpannableString content = StringUtils.getItemContent(
                    this, etWriteContent, sb.toString());
            etWriteContent.setText(content);
            etWriteContent.setSelection(curPosition + emotionName.length());

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_FROM_ALBUM:
                if (resultCode == RESULT_CANCELED) {
                    return;
                }
                Uri imageUri = data.getData();

                imgUris.add(imageUri);
                updateImgs();
                break;
            case ImageUtils.REQUEST_CODE_FROM_CAMERA:
                if (resultCode == RESULT_CANCELED) {
                    ImageUtils.deleteImageUri(this, ImageUtils.imageUriFromCamera);
                } else {
                    Uri imageUriCamera = ImageUtils.imageUriFromCamera;

                    imgUris.add(imageUriCamera);
                    updateImgs();
                }
                break;

            default:
                break;
        }
    }

    private void updateImgs() {
        if (imgUris.size() > 0) {
            gvWriteTopicImgs.setVisibility(View.VISIBLE);
            writeTopicGridImgsAdapter.notifyDataSetChanged();
        } else {
            gvWriteTopicImgs.setVisibility(View.GONE);
        }
    }

    /**
     * 发送帖子
     */
    private void sendTopic() {
        String htmlCode = etWriteContent.getText().toString();
        htmlCode = htmlCode.replace("\n", "<br/>");
        htmlCode = htmlCode.replace("emoji", "");
        for (String imgAttach : imgAttachs) {
            htmlCode += imgAttach;
        }
        SendTopicRequest sendTopicRequest = new SendTopicRequest();
        sendTopicRequest.setTitle(etWriteTitle.getText().toString());
        sendTopicRequest.setDescription(" ");
        sendTopicRequest.setHtmlcode(htmlCode);
        sendTopicRequest.setPostUserId(userId);
        ApiHelper.getInstance().sendTopic(sendTopicRequest, new SimpleResponseListener<String>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String returnMessage = jsonObject.getString("errMessage");
                    if ("发帖成功".equals(returnMessage)) {
                        Toast.makeText(WriteTopicActivity.this, "发帖成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(WriteTopicActivity.this, "发送失败，请再次尝试", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 保存帖子
     */
    private void saveTopic() {
        DraftTopic draftTopic = new DraftTopic();
        draftTopic.setTitle(etWriteTitle.getText().toString());
        draftTopic.setContent(etWriteContent.getText().toString());
        draftTopic.setImgUris(imgUris);
        Date date = new Date();
        String now = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        draftTopic.setCreateTime(now);
        draftTopic.setModifyTime(now);
        MyDbHelper dbHelper = new MyDbHelper(this);
        dbHelper.saveDraft(draftTopic);
        Toast.makeText(this, "已保存至草稿箱", Toast.LENGTH_SHORT).show();
    }
}
