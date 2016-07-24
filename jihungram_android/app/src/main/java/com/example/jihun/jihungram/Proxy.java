package com.example.jihun.jihungram;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;


/**
 * Created by jihun on 2016-07-20.
 */

public class Proxy{
    public static final String SERVER_URL = "jihungram.herokuapp.com/";
    private AsyncHttpClient client = new AsyncHttpClient();
    private User users[];
    private Article articles[];
    private AdminActivity adminActivity;

    public Proxy(AdminActivity adminActivity) {
        this.adminActivity = adminActivity;
    }

    public void admin_user_get() {
        client.get(Proxy.SERVER_URL + "api/user", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                String jsonData = new String(bytes);
                try {
                    JSONObject object = new JSONObject(jsonData);
                    JSONArray jArr = object.getJSONArray("objects");
                    Log.e("test", "___" + jsonData);
                    users = new User[jArr.length()];
                    Log.e("test", "___" + jArr.length());
                    String id, connDate, token;
                    for(int idx=0; idx<jArr.length(); idx++) {
                        JSONObject jObj = jArr.getJSONObject(idx);
                        id = jObj.getString("id");
                        connDate = jObj.getString("connDate");
                        token = jObj.getString("token");
                        users[idx] = new User(id, connDate, token);
                    }
                    adminActivity.setUser(users);
                }catch(JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    public void admin_article_get() {
        client.get(Proxy.SERVER_URL + "api/article", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                String jsonData = new String(bytes);
                try {
                    JSONObject object = new JSONObject(jsonData);
                    JSONArray jArr = object.getJSONArray("objects");

                    articles = new Article[jArr.length()];

                    int articleNumber;
                    String title;
                    String writer;
                    String id;
                    String content;
                    String writeDate;
                    String imgName;
                    int hits;

                    for(int idx=0; idx<jArr.length(); idx++) {
                        JSONObject jObj = jArr.getJSONObject(idx);

                        articleNumber = jObj.getInt("articleNumber");
                        title = jObj.getString("title");
                        writer = jObj.getString("writer");
                        id = jObj.getString("id");
                        content = jObj.getString("content");
                        writeDate = jObj.getString("writeDate");
                        imgName = jObj.getString("imgName");
                        hits = jObj.getInt("hits");

                        articles[idx] = new Article(articleNumber, title, writer, id, content, writeDate, imgName, hits);
                    }
                    adminActivity.setArticle(articles);
                }catch(JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    public void admin_user_delete(String id) {
        client.delete(Proxy.SERVER_URL + "api/user/" + id, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                Log.i("test", "Delete Success");
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i("test", "Delete failure");
            }
        });

    }

    public void admin_article_delete(String articleNumber) {
        client.delete(Proxy.SERVER_URL + "api/article/" + articleNumber, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                Log.i("test", "Delete Success");
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i("test", "Delete failure");
            }
        });
    }

}
