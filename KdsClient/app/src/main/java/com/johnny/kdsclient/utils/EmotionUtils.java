package com.johnny.kdsclient.utils;

import com.johnny.kdsclient.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/11
 */
public class EmotionUtils {

    public static Map<String, Integer> emojiMap;

    static {
        emojiMap = new HashMap<String, Integer>();
        emojiMap.put("[呵呵]", R.mipmap.ico1);
        emojiMap.put("[嘻嘻]", R.mipmap.ico2);
        emojiMap.put("[哈哈]", R.mipmap.ico3);
        emojiMap.put("[爱你]", R.mipmap.ico4);
        emojiMap.put("[挖鼻屎]", R.mipmap.ico5);
        emojiMap.put("[吃惊]", R.mipmap.ico6);
        emojiMap.put("[晕]", R.mipmap.ico7);
        emojiMap.put("[泪]", R.mipmap.ico8);
        emojiMap.put("[馋嘴]", R.mipmap.ico9);
        emojiMap.put("[抓狂]", R.mipmap.ico10);
        emojiMap.put("[哼]", R.mipmap.ico11);
        emojiMap.put("[可爱]", R.mipmap.ico12);
        emojiMap.put("[怒]", R.mipmap.ico13);
        emojiMap.put("[汗]", R.mipmap.ico14);
        emojiMap.put("[害羞]", R.mipmap.ico15);
        emojiMap.put("[睡觉]", R.mipmap.ico16);
        emojiMap.put("[钱]", R.mipmap.ico17);
        emojiMap.put("[偷笑]", R.mipmap.ico18);
        emojiMap.put("[笑cry]", R.mipmap.ico19);
        emojiMap.put("[doge]", R.mipmap.ico20);
        emojiMap.put("[喵喵]", R.mipmap.ico21);
        emojiMap.put("[酷]", R.mipmap.ico22);
        emojiMap.put("[衰]", R.mipmap.ico23);
        emojiMap.put("[闭嘴]", R.mipmap.ico24);
        emojiMap.put("[鄙视]", R.mipmap.ico25);
        emojiMap.put("[花心]", R.mipmap.ico26);
        emojiMap.put("[鼓掌]", R.mipmap.ico27);
        emojiMap.put("[悲伤]", R.mipmap.ico28);
        emojiMap.put("[思考]", R.mipmap.ico29);
        emojiMap.put("[生病]", R.mipmap.ico30);
        emojiMap.put("[亲亲]", R.mipmap.ico31);
        emojiMap.put("[怒骂]", R.mipmap.ico32);
        emojiMap.put("[太开心]", R.mipmap.ico33);
        emojiMap.put("[懒得理你]", R.mipmap.ico34);
        emojiMap.put("[右哼哼]", R.mipmap.ico35);
        emojiMap.put("[左哼哼]", R.mipmap.ico36);
        emojiMap.put("[嘘]", R.mipmap.ico37);
        emojiMap.put("[委屈]", R.mipmap.ico38);
        emojiMap.put("[吐]", R.mipmap.ico39);
        emojiMap.put("[可怜]", R.mipmap.ico40);
        emojiMap.put("[打哈气]", R.mipmap.ico41);
        emojiMap.put("[挤眼]", R.mipmap.ico42);
        emojiMap.put("[失望]", R.mipmap.ico43);
        emojiMap.put("[顶]", R.mipmap.ico44);
        emojiMap.put("[疑问]", R.mipmap.ico45);
        emojiMap.put("[困]", R.mipmap.ico46);
        emojiMap.put("[感冒]", R.mipmap.ico47);
        emojiMap.put("[拜拜]", R.mipmap.ico48);
        emojiMap.put("[黑线]", R.mipmap.ico49);
        emojiMap.put("[阴险]", R.mipmap.ico50);
        emojiMap.put("[打脸]", R.mipmap.ico51);
        emojiMap.put("[傻眼]", R.mipmap.ico52);
        emojiMap.put("[猪头]", R.mipmap.ico55);
        emojiMap.put("[熊猫]", R.mipmap.ico56);
        emojiMap.put("[兔子]", R.mipmap.ico57);
        emojiMap.put("[兔子]", R.mipmap.ico58);
        emojiMap.put("[兔子]", R.mipmap.ico59);
        emojiMap.put("[兔子]", R.mipmap.ico60);
        emojiMap.put("[兔子]", R.mipmap.ico61);
        emojiMap.put("[兔子]", R.mipmap.ico62);
        emojiMap.put("[兔子]", R.mipmap.ico63);
        emojiMap.put("[兔子]", R.mipmap.ico64);
        emojiMap.put("[兔子]", R.mipmap.ico65);
        emojiMap.put("[兔子]", R.mipmap.ico66);
        emojiMap.put("[兔子]", R.mipmap.ico67);
        emojiMap.put("[兔子]", R.mipmap.ico68);
        emojiMap.put("[兔子]", R.mipmap.ico69);
        emojiMap.put("[兔子]", R.mipmap.ico70);
        emojiMap.put("[兔子]", R.mipmap.ico71);
        emojiMap.put("[兔子]", R.mipmap.ico72);
        emojiMap.put("[兔子]", R.mipmap.ico73);
        emojiMap.put("[兔子]", R.mipmap.ico74);
        emojiMap.put("[兔子]", R.mipmap.ico75);
        emojiMap.put("[兔子]", R.mipmap.ico76);
        emojiMap.put("[兔子]", R.mipmap.ico77);
        emojiMap.put("[兔子]", R.mipmap.ico78);
        emojiMap.put("[兔子]", R.mipmap.ico79);
        emojiMap.put("[兔子]", R.mipmap.ico80);
    }

    public static int getImgByName(String imgName) {
        Integer integer = emojiMap.get(imgName);
        return integer == null ? -1 : integer;
    }
}
