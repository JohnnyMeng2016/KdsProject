package com.johnny.kdsclient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.johnny.kdsclient.bean.DraftTopic;

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
        db.execSQL(createDraftTopicSql);
        db.execSQL(createDraftImageSql);
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

            draftTopicList = new ArrayList<DraftTopic>();
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
                    DraftTopic draftTopic = draftTopicList.get(draftTopicList.size()-1);
                    draftTopic.getImgUris().add(Uri.parse(imageUri));
                }
                preId = personid;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return draftTopicList;
    }

}
