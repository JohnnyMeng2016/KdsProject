package com.johnny.kdsclient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.johnny.kdsclient.bean.DraftTopic;
import com.johnny.kdsclient.bean.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：NewKdsClient
 * 类描述：数据库操作类
 * 创建人：孟忠明
 * 创建时间：2016/11/15
 */
public class MyDbHelper extends SQLiteOpenHelper {

    public MyDbHelper(Context context) {
        super(context, "kds.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDraftTopicSql = "CREATE TABLE draft_topic(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title NVARCHAR(50), content NVARCHAR(5000), createTime VARCHAR(30),modifyTime VARCHAR(30))";
        String createDraftImageSql = "CREATE TABLE draft_image(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "topicId INTEGER, imageUri VARCHAR(50))";
        String createCollectImageSql = "CREATE TABLE collect(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "bbsId VARCHAR(50), areaId VARCHAR(50), title VARCHAR(200), userId VARCHAR(50), " +
                "nickName VARCHAR(50), replyNickName VARCHAR(50), createTime VARCHAR(50), postTime VARCHAR(50)," +
                "replyTime VARCHAR(50), postTS VARCHAR(50), replyTS VARCHAR(50), view INTEGER, reply INTEGER, " +
                "currentUserId VARCHAR(50), userLog VARCHAR(50), description VARCHAR(200), preview VARCHAR(50))";
        db.execSQL(createDraftTopicSql);
        db.execSQL(createDraftImageSql);
        db.execSQL(createCollectImageSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 保存草稿
     *
     * @param draftTopic
     */
    public void saveDraft(DraftTopic draftTopic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", draftTopic.getTitle());
        contentValues.put("content", draftTopic.getContent());
        contentValues.put("createTime", draftTopic.getCreateTime());
        contentValues.put("modifyTime", draftTopic.getModifyTime());
        long topicId = db.insert("draft_topic", null, contentValues);
        if (null != draftTopic.getImgUris() && draftTopic.getImgUris().size() > 0) {
            for (Uri imgUri : draftTopic.getImgUris()) {
                ContentValues values = new ContentValues();
                values.put("topicId", topicId);
                values.put("imageUri", imgUri.toString());
                db.insert("draft_image", null, values);
            }
        }
    }

    /**
     * 更新草稿
     *
     * @param draftTopic
     */
    public void updateDraft(DraftTopic draftTopic) {
        deleteDraft(draftTopic);
        saveDraft(draftTopic);
    }

    /**
     * 删除草稿
     *
     * @param draftTopic
     */
    public void deleteDraft(DraftTopic draftTopic) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("draft_topic", "id = ?", new String[]{String.valueOf(draftTopic.getId())});
        db.delete("draft_image", "topicId=?", new String[]{String.valueOf(draftTopic.getId())});
    }

    /**
     * 查询草稿列表
     */
    public List<DraftTopic> searchDraftList() {
        List<DraftTopic> draftTopicList = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select a.*,b.imageUri from draft_topic a left join draft_image b on a.id = b.topicId";
        Cursor cursor = db.rawQuery(sql, null);
        //存在数据才返回true
        if (cursor.moveToFirst()) {

            draftTopicList = new ArrayList<>();
            int preId = 0;
            do {
                int personid = cursor.getInt(cursor.getColumnIndex("id"));
                if (preId != personid) {
                    DraftTopic draftTopic = new DraftTopic();
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String content = cursor.getString(cursor.getColumnIndex("content"));
                    String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
                    String modifyTime = cursor.getString(cursor.getColumnIndex("modifyTime"));
                    String imageUri = cursor.getString(cursor.getColumnIndex("imageUri"));
                    if (null != imageUri && !"".equals(imageUri)) {
                        draftTopic.setImgUris(new ArrayList<Uri>());
                        draftTopic.getImgUris().add(Uri.parse(imageUri));
                    }
                    draftTopic.setTitle(title);
                    draftTopic.setContent(content);
                    draftTopic.setCreateTime(createTime);
                    draftTopic.setModifyTime(modifyTime);
                    draftTopicList.add(draftTopic);
                } else {
                    String imageUri = cursor.getString(cursor.getColumnIndex("imageUri"));
                    DraftTopic draftTopic = draftTopicList.get(draftTopicList.size() - 1);
                    draftTopic.getImgUris().add(Uri.parse(imageUri));
                }
                preId = personid;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return draftTopicList;
    }

    /**
     * 保存收藏
     *
     * @param topic
     */
    public void saveCollect(Topic topic) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from collect where bbsId='" + topic.getBbsId() + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {//改贴已在收藏表
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("bbsId", topic.getBbsId());
        contentValues.put("areaId", topic.getAreaId());
        contentValues.put("title", topic.getTitle());
        contentValues.put("userId", topic.getUserId());
        contentValues.put("nickName", topic.getNickName());
        contentValues.put("replyNickName", topic.getReplyNickName());
        contentValues.put("createTime", topic.getCreateTime());
        contentValues.put("postTime", topic.getPostTime());
        contentValues.put("replyTime", topic.getReplyTime());
        contentValues.put("postTS", topic.getPostTS());
        contentValues.put("replyTS", topic.getReplyTS());
        contentValues.put("view", topic.getView());
        contentValues.put("reply", topic.getReply());
        contentValues.put("currentUserId", topic.getCurrentUserId());
        contentValues.put("userLog", topic.getUserLog());
        contentValues.put("description", topic.getDescription());
        contentValues.put("preview", topic.getPreview());
        db.insert("collect", null, contentValues);
    }

    /**
     * 删除收藏
     *
     * @param topic
     */
    public void deleteCollect(Topic topic) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("collect", "bbsId = ?", new String[]{String.valueOf(topic.getBbsId())});
    }

    /**
     * 查询收藏列表
     */
    public List<Topic> searchCollectList() {
        List<Topic> topicList = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from collect";
        Cursor cursor = db.rawQuery(sql, null);
        //存在数据才返回true
        if (cursor.moveToFirst()) {
            topicList = new ArrayList<>();
            do {
                Topic topic = new Topic();
                topic.setBbsId(cursor.getString(cursor.getColumnIndex("bbsId")));
                topic.setAreaId(cursor.getString(cursor.getColumnIndex("areaId")));
                topic.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                topic.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
                topic.setReplyNickName(cursor.getString(cursor.getColumnIndex("replyNickName")));
                topic.setCreateTime(cursor.getString(cursor.getColumnIndex("createTime")));
                topic.setPostTime(cursor.getString(cursor.getColumnIndex("postTime")));
                topic.setReplyTime(cursor.getString(cursor.getColumnIndex("replyTime")));
                topic.setPostTS(cursor.getString(cursor.getColumnIndex("postTS")));
                topic.setReplyTS(cursor.getString(cursor.getColumnIndex("replyTS")));
                topic.setView(cursor.getInt(cursor.getColumnIndex("view")));
                topic.setReply(cursor.getInt(cursor.getColumnIndex("reply")));
                topic.setCurrentUserId(cursor.getString(cursor.getColumnIndex("currentUserId")));
                topic.setUserLog(cursor.getString(cursor.getColumnIndex("userLog")));
                topic.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                topic.setPreview(cursor.getString(cursor.getColumnIndex("preview")));
                topicList.add(topic);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return topicList;
    }

}
