package com.example.jihun.jihungram;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Administrator on 2016-07-21.
 */
public class ProxyUP {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void uploadArticle(Context context, Article article, String filePath, AsyncHttpResponseHandler responseHandler){

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("title", article.getTitle());
            jsonParams.put("writer", article.getWriter());
            jsonParams.put("id", article.getId());
            jsonParams.put("content", article.getContent());
            jsonParams.put("writeDate", article.getWriteDate());
            jsonParams.put("imgName", article.getImgName());

            StringEntity entity = new StringEntity(jsonParams.toString(), "UTF-8");

            client.post(context, Proxy.SERVER_URL + "api/article", entity, "application/json", responseHandler);
        }catch (Exception e) {
            e.printStackTrace();
        }

        RequestParams imgParams = new RequestParams();

        try {
            imgParams.put("imageFile", new File(filePath));
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        client.post(Proxy.SERVER_URL + "imageUp", imgParams,  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                String jsonData = new String(bytes);
                Log.i("test", "Image upload Success");
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i("test", "Image upload Failure");
            }
        });
        client.get(Proxy.SERVER_URL + "requestPush",  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                String jsonData = new String(bytes);
                Log.i("test", "requestPush Success");
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i("test", "requestPush Failure");
            }
        });
    }

    public static void uploadUser(Context context, String id, String date, String token){
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("id", id);
            jsonParams.put("connDate", date);
            jsonParams.put("token", token);

            StringEntity entity = new StringEntity(jsonParams.toString(), "UTF-8");
            client.post(context, Proxy.SERVER_URL + "api/user", entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.e("test", "user upload");

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("test", "user upload fail");

                }
            });
        }catch (Exception e) {

        }
    }


    public static void adminArticleUpload(Context context, String title, String writer, String id, String content, String writeDate, String ImgName, String hits){
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("title", title);
            jsonParams.put("writer", writer);
            jsonParams.put("id", id);
            jsonParams.put("content", content);
            jsonParams.put("writeDate", writeDate);
            jsonParams.put("ImgName", ImgName);
            jsonParams.put("hits", hits);

            StringEntity entity = new StringEntity(jsonParams.toString(), "UTF-8");
            client.post(context, Proxy.SERVER_URL + "api/article", entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.e("test", "user upload");

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("test", "user upload fail");

                }
            });
        }catch (Exception e) {

        }
    }
}
