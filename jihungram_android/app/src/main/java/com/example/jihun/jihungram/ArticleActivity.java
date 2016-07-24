package com.example.jihun.jihungram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jihun on 2016-07-16.
 */
public class ArticleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_article);

        TextView tvTitle = (TextView) findViewById(R.id.view_title);
        TextView tvWriter = (TextView) findViewById(R.id.view_writer);
        TextView tvContent = (TextView) findViewById(R.id.view_content);
        TextView tvWriteTime = (TextView) findViewById(R.id.view_date);
        ImageView tvImage = (ImageView) findViewById(R.id.view_image);

        String articleNumber = getIntent().getExtras().getString("ArticleNumber");

        Dao dao = new Dao(getApplicationContext());

        Article article = dao.getArticleByArticleNumber(Integer.parseInt(articleNumber));

        tvTitle.setText(article.getTitle());
        tvWriter.setText(article.getWriter());
        tvContent.setText(article.getContent());
        tvWriteTime.setText(article.getWriteDate());

        String img_path = getApplication().getFilesDir().getPath() + "/" + article.getImgName();
        File img_load_path = new File(img_path);

        if(img_load_path.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(img_path);
            tvImage.setImageBitmap(bitmap);
        }
    }
}
