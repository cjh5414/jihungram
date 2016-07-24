package com.example.jihun.jihungram;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jihun on 2016-07-16.
 */
public class ArticleWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etWriter;
    private EditText etTitle;
    private EditText etContent;
    private ImageButton imgBtn;
    private ImageButton buUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_article);

        etWriter = (EditText)findViewById(R.id.write_editText_writer);
        etTitle = (EditText)findViewById(R.id.write_editText_title);
        etContent = (EditText)findViewById(R.id.write_editText_content);
        imgBtn = (ImageButton)findViewById(R.id.write_imgBtn);
        buUpload = (ImageButton)findViewById(R.id.write_btn);

        imgBtn.setOnClickListener(this);
        buUpload.setOnClickListener(this);
    }

    private String filePath;
    private String fileName;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == REQUEST_PHOTO_ALBOM) {
                Uri uri = getRealPathRui(data.getData());
                filePath = uri.toString();
                fileName = uri.getLastPathSegment();

                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                imgBtn.setImageBitmap(bitmap);
            }
        }catch(Exception e) {
            Log.e("test", "onActivityResult ERROR:" + e);
        }
    }

    private Uri getRealPathRui(Uri uri) {
        Uri filePathUri = uri;
        if(uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
            if(cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePathUri = Uri.parse(cursor.getString(column_index));
            }
        }
        return filePathUri;
    }

    private static final int REQUEST_PHOTO_ALBOM = 1;

    @Override
    public void onClick(View arg0) {
        switch(arg0.getId()) {
            case R.id.write_imgBtn:
                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PHOTO_ALBOM);
                break;
            case R.id.write_btn:
                final Handler handler = new Handler();
                final ProgressDialog progressDialog = ProgressDialog.show(ArticleWriteActivity.this, "", "업로드 중 입니다.");

                String ID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                String DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA).format(new Date());

                Article article = new Article(
                        00,
                        etTitle.getText().toString(),
                        etWriter.getText().toString(),
                        ID,
                        etContent.getText().toString(),
                        DATE,
                        fileName,
                        0);
                ProxyUP.uploadArticle(getApplicationContext(), article, filePath, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("test", "up onSuccess" + statusCode);
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e("test", "up onFailure:" + statusCode);
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(), "등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
