package com.example.jihun.jihungram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Administrator on 2016-07-23.
 */
public class RankActivity extends AppCompatActivity {
    TextView title[] = new TextView[3];
    TextView content[] = new TextView[3];
    ImageView imgView[] = new ImageView[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);

        title[0] = (TextView)findViewById(R.id.rank_title1);
        title[1] = (TextView)findViewById(R.id.rank_title2);
        title[2] = (TextView)findViewById(R.id.rank_title3);
        content[0] = (TextView)findViewById(R.id.rank_content1);
        content[1] = (TextView)findViewById(R.id.rank_content2);
        content[2] = (TextView)findViewById(R.id.rank_content3);
        imgView[0] = (ImageView)findViewById(R.id.rank_imgView1);
        imgView[1] = (ImageView)findViewById(R.id.rank_imgView2);
        imgView[2] = (ImageView)findViewById(R.id.rank_imgView3);

        Intent intent = new Intent(this.getIntent());
        String img_path = getApplication().getFilesDir().getPath() + "/";

        for(int i=0; i<3; i++) {
            title[i].setText(intent.getStringExtra("Title" + (i+1)));
            content[i].setText(intent.getStringExtra("Content" + (i+1)));
            Bitmap bitmap = BitmapFactory.decodeFile(img_path + intent.getStringExtra("ImgName" + (i+1)));
            imgView[i].setImageBitmap(bitmap);
        }
    }
}
