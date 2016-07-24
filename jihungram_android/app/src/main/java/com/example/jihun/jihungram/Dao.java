package com.example.jihun.jihungram;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jihun on 2016-07-17.
 */
public class Dao {
    private Context context;
    private SQLiteDatabase database;

    public Dao(Context context) {
        this.context = context;

        // SQLite 초기화
        database = context.openOrCreateDatabase("LocalDATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        // 테이블 생성
        try {
            String sql = "CREATE TABLE IF NOT EXISTS Articles (ID integer primary key autoincrement, "
                    + "                                         ArticleNumber integer UNIQUE not null, "
                    + "                                         Title text not null,"
                    + "                                         WriterName text not null,"
                    + "                                         WriterID text not null,"
                    + "                                         Content text not null,"
                    + "                                         WriteDate text not null,"
                    + "                                         ImgName text not null,"
                    + "                                         Hits Integer not null);";
            database.execSQL(sql);
        } catch (Exception e) {
            Log.e("test", "CREATE TABLE FAILED! -" + e);
            e.printStackTrace();
        }
    }
    public void insertJsonData(String jsonData) {
        int articleNumber;
        String title;
        String writer;
        String id;
        String content;
        String writeDate;
        String imgName;
        int hits;

        FIleDownloader fIleDownloader = new FIleDownloader(context);

        try {
            JSONObject object = new JSONObject(jsonData);
            JSONArray jArr = object.getJSONArray("objects");


            for(int i=0; i< jArr.length(); i++) {
                JSONObject jObj = jArr.getJSONObject(i);

                articleNumber = jObj.getInt("articleNumber");
                title = jObj.getString("title");
                writer = jObj.getString("writer");
                id = jObj.getString("id");
                content = jObj.getString("content");
                writeDate = jObj.getString("writeDate");
                imgName = jObj.getString("imgName");
                hits = jObj.getInt("hits");

                Log.i("test", "ArticleNumber: " +articleNumber + " Title:" + title);

                // img 파일 다운로드
                fIleDownloader.downFile(Proxy.SERVER_URL + "imageDown/" + imgName, imgName);

                String sql = "INSERT INTO Articles(ArticleNumber, Title, WriterName, WriterID, Content, WriteDate, ImgName, Hits)"
                        + " VALUES(" + articleNumber + ",'" + title + "','" + writer + "','" + id + "', '" + content + "', '"+ writeDate + "','" + imgName + "','" + hits +"');";
                try {
                    database.execSQL(sql);
                } catch(Exception e ) {
                    e.printStackTrace();
                }
            }

        } catch(JSONException e) {
            Log.e("test", "JSON ERROR! -" +e);
            e.printStackTrace();
        }
    }

    public ArrayList<Article> getArticleList() {
        ArrayList<Article> articleList = new ArrayList<Article>();

        int articleNumber;
        String title;
        String writer;
        String id;
        String content;
        String writeDate;
        String imgName;
        int hits;

        String sql = "SELECT * FROM Articles;";
        Cursor cursor = database.rawQuery(sql, null);

        while(cursor.moveToNext()) {
            articleNumber = cursor.getInt(1);
            title = cursor.getString(2);
            writer = cursor.getString(3);
            id = cursor.getString(4);
            content = cursor.getString(5);
            writeDate = cursor.getString(6);
            imgName = cursor.getString(7);
            hits = cursor.getInt(8);

            articleList.add(new Article(articleNumber, title, writer, id, content, writeDate, imgName, hits));
        }
        cursor.close();
        return articleList;
    }

    public Article getArticleByArticleNumber(int articleNumber) {
        Article article = null;

        String title;
        String writer;
        String id;
        String content;
        String writeDate;
        String imgName;
        int hits;

        String sql = "SELECT * FROM Articles WHERE ArticleNumber = " + articleNumber + ";";
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToNext();

        articleNumber = cursor.getInt(1);
        title = cursor.getString(2);
        writer = cursor.getString(3);
        id = cursor.getString(4);
        content = cursor.getString(5);
        writeDate = cursor.getString(6);
        imgName = cursor.getString(7);
        hits = cursor.getInt(8);

        article = new Article(articleNumber, title, writer, id, content, writeDate, imgName, hits);

        cursor.close();
        return article;
    }

    public void clearDB() {
        try {
            String sql = "delete from Articles;";
            database.execSQL(sql);
        } catch(Exception e ) {
            e.printStackTrace();
        }
    }
}
