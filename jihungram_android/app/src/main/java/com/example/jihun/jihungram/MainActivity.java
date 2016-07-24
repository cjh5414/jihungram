package com.example.jihun.jihungram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView mainListView;
    private ArrayList<Article> articleList;
    private static AsyncHttpClient client = new AsyncHttpClient();
    Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton writeBtn = (ImageButton) findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(this);
        ImageButton refreshBtn = (ImageButton) findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(this);
        ImageButton rankBtn = (ImageButton) findViewById(R.id.rankBtn);
        rankBtn.setOnClickListener(this);

        mainListView = (ListView) findViewById(R.id.listView);
        mainListView.setOnItemClickListener(this);

        dao = new Dao(getApplicationContext());

        refreshData();
    }
    public void refreshData() {
        client.get(Proxy.SERVER_URL + "api/article", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                String jsonData = new String(bytes);
                Log.i("test", "jsonData: " + jsonData);

                dao.insertJsonData(jsonData);

                listView();
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }
    private void listView() {
        articleList = dao.getArticleList();
        Log.i("test", "articleList Count : " + articleList.size());

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.custom_list_row, articleList);
        mainListView.setAdapter(customAdapter);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.writeBtn:
                Intent intent = new Intent(this, ArticleWriteActivity.class);
                startActivity(intent);
                break;
            case R.id.refreshBtn:
                dao.clearDB();
                refreshData();
                break;
            case R.id.rankBtn:
                ArrayList<Article> myArrayData = new ArrayList<Article>();
                for(int i=0; i<articleList.size(); i++) {
                    myArrayData.add(articleList.get(i));
                    Log.i("test", "mmmm" + i + myArrayData.get(i).getHits());
                }
                Comparator<Article> myComparator = new Comparator<Article>() {
                    private final Collator collator = Collator.getInstance();

                    @Override
                    public int compare(Article article1, Article article2) {
                        return (article1.getHits() > article2.getHits() ? 1: -1);
                    }
                };

                Collections.sort(myArrayData, myComparator);
                Collections.reverse(myArrayData);
                for(int i=0; i<myArrayData.size(); i++) {
                    Log.i("test", "mmmm" + i + myArrayData.get(i).getHits());
                }

                Intent intent_rank = new Intent(this, RankActivity.class);


                for(int i=0; i<3; i++) {
                    intent_rank.putExtra("Title" + (i + 1), myArrayData.get(i).getTitle());
                    intent_rank.putExtra("Content" + (i + 1), myArrayData.get(i).getContent());
                    intent_rank.putExtra("ImgName" + (i+1), myArrayData.get(i).getImgName());
                }

                startActivity(intent_rank);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        client.get(Proxy.SERVER_URL + "hitsUp/" + articleList.get(position).getArticleNumber(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {

            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra("ArticleNumber", articleList.get(position).getArticleNumber() + "");
        startActivity(intent);
    }
}
