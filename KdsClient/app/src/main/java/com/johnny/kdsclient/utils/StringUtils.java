package com.johnny.kdsclient.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/11
 */
public class StringUtils {

    private StringUtils() {
    }

    public static SpannableString getItemContent(Context context, TextView tv, String content) {
        String regexAt = "@[\u4e00-\u9fa5\\w]+";
        String regexTopic = "#[\u4e00-\u9fa5\\w]+#";
        String regexEmoji = "\\[[\u4e00-\u9fa5\\w]+\\]";

//        "<img src=\\\"http://jscss.kdslife.com/club/html/images/icon/jingdian/ico_0005.gif\\\" alt=\\\"http://jscss.kdslife.com/club/html/images/icon/jingdian/ico_0005.gif\\\" />"
        String regex = "(" + regexAt + ")|(" + regexTopic + ")|(" + regexEmoji + ")";

        SpannableString spannableString = new SpannableString(content);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(spannableString);

        if (matcher.find()) {
            tv.setMovementMethod(LinkMovementMethod.getInstance());
            matcher.reset();
        }

        while (matcher.find()) {
            final String topicStr = matcher.group(2);
            String emojiStr = matcher.group(3);

            if (emojiStr != null) {
                int start = matcher.start(3);

                int imgRes = EmotionUtils.getImgByName(emojiStr);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imgRes);

                if (bitmap != null) {
                    int size = (int) tv.getTextSize();
                    bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                    ImageSpan imageSpan = new ImageSpan(context, bitmap);
                    spannableString.setSpan(imageSpan, start, start + emojiStr.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }


        }


        return spannableString;
    }
}
