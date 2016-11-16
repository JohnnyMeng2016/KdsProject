package com.johnny.kdsclient.bean;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 项目名称：NewKdsClient
 * 类描述：发帖草稿
 * 创建人：孟忠明
 * 创建时间：2016/11/15
 */
public class DraftTopic implements Parcelable {
    private int id;
    private String title;
    private String content;
    private String createTime;
    private String modifyTime;
    private List<Uri> imgUris;

    public DraftTopic() {
    }

    protected DraftTopic(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        createTime = in.readString();
        modifyTime = in.readString();
        imgUris = in.createTypedArrayList(Uri.CREATOR);
    }

    public static final Creator<DraftTopic> CREATOR = new Creator<DraftTopic>() {
        @Override
        public DraftTopic createFromParcel(Parcel in) {
            return new DraftTopic(in);
        }

        @Override
        public DraftTopic[] newArray(int size) {
            return new DraftTopic[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public List<Uri> getImgUris() {
        return imgUris;
    }

    public void setImgUris(List<Uri> imgUris) {
        this.imgUris = imgUris;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(createTime);
        dest.writeString(modifyTime);
        dest.writeTypedList(imgUris);
    }
}
