package com.johnny.kdsclient.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.UserData;
import com.johnny.kdsclient.adapter.EmotionGvAdapter;
import com.johnny.kdsclient.adapter.EmotionPagerAdapter;
import com.johnny.kdsclient.adapter.WriteTopicGridImgsAdapter;
import com.johnny.kdsclient.api.ApiHelper;
import com.johnny.kdsclient.api.SimpleResponseListener;
import com.johnny.kdsclient.bean.CommonResponse;
import com.johnny.kdsclient.bean.Topic;
import com.johnny.kdsclient.utils.CommonUtils;
import com.johnny.kdsclient.utils.EmotionUtils;
import com.johnny.kdsclient.utils.ImageUtils;
import com.johnny.kdsclient.utils.JsonParser;
import com.johnny.kdsclient.utils.StringUtils;
import com.johnny.kdsclient.utils.ThemeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名称：NewKdsClient
 * 类描述：回复界面
 * 创建人：孟忠明
 * 创建时间：2016/11/18
 */
public class ReplyTopicActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    private Topic topic;
    private String userId;

    // 语音听写UI
    private RecognizerDialog mIatDialog;

    @Override
    protected void configTheme() {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.BaseAppTheme_NoActionBarDialog, R.style.BaseAppThemeDark_NoActionBarDialog);
    }

    @Override
    protected int layout() {
        return R.layout.activity_write_reply;
    }

    @Override
    protected void initDate() {
        userId = UserData.getInstance().getUserInfo().getUserId();
        topic = (Topic) getIntent().getSerializableExtra("topic");
        initEmotion();

        mIatDialog = new RecognizerDialog(this, mTtsInitListener);
    }

    @Override
    protected void initView() {
        setBackEnable(false);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        Point point = new Point();
        d.getSize(point);
        p.height = (int) (point.y * 0.7); // 高度设置为屏幕的0.8
        p.width = point.x;
        getWindow().setGravity(Gravity.LEFT | Gravity.BOTTOM);
        getWindow().setAttributes(p);

        toolbar.setTitle("编辑回复");
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        writeTopicGridImgsAdapter = new WriteTopicGridImgsAdapter(this, imgUris, gvWriteTopicImgs);
        gvWriteTopicImgs.setAdapter(writeTopicGridImgsAdapter);
        gvWriteTopicImgs.setOnItemClickListener(this);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write_reply, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_send) {//发送
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
                            Toast.makeText(ReplyTopicActivity.this, "发送失败，请再次尝试", Toast.LENGTH_SHORT)
                                    .show();
                        }

                        @Override
                        public void onResponse(String response) {
                            imgAttachs.add(response);
                            if (imgCount == imgAttachs.size()) {
                                sendReply();
                            }
                        }
                    });
                }
            } else {
                sendReply();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
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
                // 显示听写对话框
                mIatDialog.setListener(mRecognizerDialogListener);
                mIatDialog.show();
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
     * 初始化语音合成监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @SuppressLint("ShowToast")
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                // showTip("初始化失败,错误码：" + code);
                Toast.makeText(getApplicationContext(), "初始化失败,错误码：" + code,
                        Toast.LENGTH_SHORT).show();
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            String text = JsonParser.parseIatResult(results.getResultString());

            String sn = null;
            // 读取json结果中的sn字段
            try {
                JSONObject resultJson = new JSONObject(results.getResultString());
                sn = resultJson.optString("sn");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            HashMap<String, String> iatResults = new LinkedHashMap<>();
            iatResults.put(sn, text);

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : iatResults.keySet()) {
                resultBuffer.append(iatResults.get(key));
            }

            String beforeContent = etWriteContent.getText().toString();
            String afterContent = beforeContent + resultBuffer.toString();
            SpannableString content = StringUtils.getItemContent(
                    ReplyTopicActivity.this, etWriteContent, afterContent);
            etWriteContent.setText(content);
            etWriteContent.setSelection(etWriteContent.length());
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
        }

    };

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
     * 回帖
     */
    private void sendReply() {
        String message = etWriteContent.getText().toString();
        message = message.replace("\n", "<br/>");
        message = message.replace("emoji", "");
        for (String imgAttach : imgAttachs) {
            message += imgAttach;
        }
        ApiHelper.getInstance().replyTopic(message, topic.getBbsId(), userId, new SimpleResponseListener<CommonResponse>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ReplyTopicActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(CommonResponse response) {
                progressDialog.dismiss();
                if (response.getFlag() == 1) {
                    Toast.makeText(ReplyTopicActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ReplyTopicActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
