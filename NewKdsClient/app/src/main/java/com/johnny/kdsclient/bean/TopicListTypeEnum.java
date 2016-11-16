package com.johnny.kdsclient.bean;

/**
 * 项目名称：NewKdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/11/16
 */
public enum TopicListTypeEnum {
    Normal("主页", 0), Daliy("今日热帖", 1), Week("本周热帖", 2), Month("本月热帖", 3);

    // 成员变量
    private String name;
    private int index;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private TopicListTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (TopicListTypeEnum c : TopicListTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
