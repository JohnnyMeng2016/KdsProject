package com.johnny.kdsclient.utils;

import com.johnny.kdsclient.R;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/11
 */
public class EmotionUtils {

    public static Map<Integer, Integer> emojiMap;

    static {
        emojiMap = new LinkedHashMap<>();
        emojiMap.put(1, R.mipmap.ico1);
        emojiMap.put(2, R.mipmap.ico2);
        emojiMap.put(3, R.mipmap.ico3);
        emojiMap.put(4, R.mipmap.ico4);
        emojiMap.put(5, R.mipmap.ico5);
        emojiMap.put(6, R.mipmap.ico6);
        emojiMap.put(7, R.mipmap.ico7);
        emojiMap.put(8, R.mipmap.ico8);
        emojiMap.put(9, R.mipmap.ico9);
        emojiMap.put(10, R.mipmap.ico10);
        emojiMap.put(11, R.mipmap.ico11);
        emojiMap.put(12, R.mipmap.ico12);
        emojiMap.put(13, R.mipmap.ico13);
        emojiMap.put(14, R.mipmap.ico14);
        emojiMap.put(15, R.mipmap.ico15);
        emojiMap.put(16, R.mipmap.ico16);
        emojiMap.put(17, R.mipmap.ico17);
        emojiMap.put(18, R.mipmap.ico18);
        emojiMap.put(19, R.mipmap.ico19);
        emojiMap.put(20, R.mipmap.ico20);
        emojiMap.put(21, R.mipmap.ico21);
        emojiMap.put(22, R.mipmap.ico22);
        emojiMap.put(23, R.mipmap.ico23);
        emojiMap.put(24, R.mipmap.ico24);
        emojiMap.put(25, R.mipmap.ico25);
        emojiMap.put(26, R.mipmap.ico26);
        emojiMap.put(27, R.mipmap.ico27);
        emojiMap.put(28, R.mipmap.ico28);
        emojiMap.put(29, R.mipmap.ico29);
        emojiMap.put(30, R.mipmap.ico30);
        emojiMap.put(31, R.mipmap.ico31);
        emojiMap.put(32, R.mipmap.ico32);
        emojiMap.put(33, R.mipmap.ico33);
        emojiMap.put(34, R.mipmap.ico34);
        emojiMap.put(35, R.mipmap.ico35);
        emojiMap.put(36, R.mipmap.ico36);
        emojiMap.put(37, R.mipmap.ico37);
        emojiMap.put(38, R.mipmap.ico38);
        emojiMap.put(39, R.mipmap.ico39);
        emojiMap.put(40, R.mipmap.ico40);
        emojiMap.put(41, R.mipmap.ico41);
        emojiMap.put(42, R.mipmap.ico42);
        emojiMap.put(43, R.mipmap.ico43);
        emojiMap.put(44, R.mipmap.ico44);
        emojiMap.put(45, R.mipmap.ico45);
        emojiMap.put(46, R.mipmap.ico46);
        emojiMap.put(47, R.mipmap.ico47);
        emojiMap.put(48, R.mipmap.ico48);
        emojiMap.put(49, R.mipmap.ico49);
        emojiMap.put(50, R.mipmap.ico50);
        emojiMap.put(51, R.mipmap.ico51);
        emojiMap.put(52, R.mipmap.ico52);
        emojiMap.put(55, R.mipmap.ico55);
        emojiMap.put(56, R.mipmap.ico56);
        emojiMap.put(57, R.mipmap.ico57);
        emojiMap.put(58, R.mipmap.ico58);
        emojiMap.put(59, R.mipmap.ico59);
        emojiMap.put(60, R.mipmap.ico60);
        emojiMap.put(61, R.mipmap.ico61);
        emojiMap.put(62, R.mipmap.ico62);
        emojiMap.put(63, R.mipmap.ico63);
        emojiMap.put(64, R.mipmap.ico64);
        emojiMap.put(65, R.mipmap.ico65);
        emojiMap.put(66, R.mipmap.ico66);
        emojiMap.put(67, R.mipmap.ico67);
        emojiMap.put(68, R.mipmap.ico68);
        emojiMap.put(69, R.mipmap.ico69);
        emojiMap.put(70, R.mipmap.ico70);
        emojiMap.put(71, R.mipmap.ico71);
        emojiMap.put(72, R.mipmap.ico72);
        emojiMap.put(73, R.mipmap.ico73);
        emojiMap.put(74, R.mipmap.ico74);
        emojiMap.put(75, R.mipmap.ico75);
        emojiMap.put(76, R.mipmap.ico76);
        emojiMap.put(77, R.mipmap.ico77);
        emojiMap.put(78, R.mipmap.ico78);
        emojiMap.put(79, R.mipmap.ico79);
        emojiMap.put(80, R.mipmap.ico80);
    }

    public static int getImgByName(int emojiCode) {
        Integer integer = emojiMap.get(emojiCode);
        return integer == null ? -1 : integer;
    }
}
