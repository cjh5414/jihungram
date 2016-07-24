package com.example.jihun.jihungram;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Administrator on 2016-07-24.
 */
public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    private Proxy proxy;
    Button userBtn, articleBtn, addBtn;
    TableLayout userTable, articleTable;
    TableRow userTableRowSample, articleTableRowSample;
    TextView textViewSample;
    LinearLayout addLayout;
    EditText edtArticleNumber, edtTitle, edtWriter, edtId, edtContent, edtWriteDate, edtImgName, edtHits;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        proxy = new Proxy(this);
        userBtn = (Button) findViewById(R.id.admin_user_btn);
        articleBtn = (Button) findViewById(R.id.admin_article_btn);
        userTable = (TableLayout) findViewById(R.id.admin_user_table);
        articleTable = (TableLayout) findViewById(R.id.admin_article_table);
        userTableRowSample = (TableRow) findViewById(R.id.admin_user_tableRow_sample);
        textViewSample = (TextView) findViewById(R.id.admin_user_textView_sample);
        articleTableRowSample = (TableRow) findViewById(R.id.admin_article_tableRow_sample);
        addBtn = (Button) findViewById(R.id.admin_add_btn);
        addLayout = (LinearLayout) findViewById(R.id.admin_add_layout);

        edtArticleNumber = (EditText)findViewById(R.id.admin_add_edt_articleNumber);
        edtTitle = (EditText)findViewById(R.id.admin_add_edt_title);
        edtWriter = (EditText)findViewById(R.id.admin_add_edt_writer);
        edtId = (EditText)findViewById(R.id.admin_add_edt_id);
        edtContent = (EditText)findViewById(R.id.admin_add_edt_content);
        edtWriteDate = (EditText)findViewById(R.id.admin_add_edt_writeDate);
        edtImgName = (EditText)findViewById(R.id.admin_add_edt_imgName);
        edtHits = (EditText)findViewById(R.id.admin_add_edt_hits);

        userBtn.setOnClickListener(this);
        articleBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        addBtn.setVisibility(View.VISIBLE);
        switch (v.getId()) {
            case R.id.admin_user_btn:
                articleTable.setVisibility(View.GONE);
                userTable.setVisibility(View.VISIBLE);
                proxy.admin_user_get();
                break;
            case R.id.admin_article_btn:
                userTable.setVisibility(View.GONE);
                articleTable.setVisibility(View.VISIBLE);
                proxy.admin_article_get();
                break;
            case R.id.admin_add_btn:
                if (addBtn.getText().toString().equals("ADD")) {
                    addBtn.setText("SUBMIT");
                    addLayout.setVisibility(View.VISIBLE);
                } else if (addBtn.getText().toString().equals("SUBMIT")) {
                    if(userTable.getVisibility()==View.VISIBLE) {
//                        if (!edtId.getText().toString().equals("") && !edtConnDate.getText().toString().equals("") && !edtConnDate.getText().toString().equals(""))
//                            ProxyUP.uploadUser(getApplicationContext(), edtId.getText().toString(), edtConnDate.getText().toString(), edtConnDate.getText().toString());
//                        edtId.setText("");
//                        edtConnDate.setText("");
//                        edtToken.setText("");
//                        addLayout.setVisibility(View.GONE);
//                        addBtn.setText("ADD");
//                        proxy.admin_user_get();
                    }
                    else {
                        if (!edtArticleNumber.getText().toString().equals("") && !edtTitle.getText().toString().equals("") && !edtWriter.getText().toString().equals(""))
                            ProxyUP.adminArticleUpload(getApplicationContext(), edtTitle.getText().toString(), edtWriter.getText().toString(), edtId.getText().toString(), edtContent.getText().toString(), edtWriteDate.getText().toString(), edtImgName.getText().toString(), edtHits.getText().toString());
                        edtArticleNumber.setText("");
                        edtTitle.setText("");
                        edtWriter.setText("");
                        edtId.setText("");
                        edtContent.setText("");
                        edtWriteDate.setText("");
                        edtImgName.setText("");
                        edtHits.setText("");
                        addLayout.setVisibility(View.GONE);
                        addBtn.setText("ADD");
                        proxy.admin_user_get();
                    }
                }
                break;
        }
    }

    public void setUser(User[] users) {
        userTable.removeAllViews();
        userTable.addView(userTableRowSample);

        String text[] = new String[3];
        for (int i = 0; i < users.length; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(userTableRowSample.getLayoutParams());

            text[0] = users[i].getId();
            text[1] = users[i].getConnDate();
            text[2] = users[i].getToken();

            TextView tv[] = new TextView[3];
            for (int j = 0; j < 3; j++) {
                tv[j] = new TextView(this);
                tv[j].setLayoutParams(textViewSample.getLayoutParams());
                tv[j].setText(text[j]);
                tv[j].setBackgroundColor(Color.WHITE);
                tv[j].setGravity(Gravity.CENTER);
                tr.addView(tv[j]);
            }
            tr.setBackgroundColor(Color.WHITE);

            Button deleteBtn = new Button(this);
            deleteBtn.setText(text[0]);
            deleteBtn.setTextColor(Color.GRAY);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    proxy.admin_user_delete(((Button) v).getText().toString());
                    proxy.admin_user_get();
                }
            });
            tr.addView(deleteBtn);

            Button updateBtn = new Button(this);
            deleteBtn.setText(text[0]);
            deleteBtn.setTextColor(Color.LTGRAY);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            tr.addView(updateBtn);
            userTable.addView(tr);
        }
    }

    public void setArticle(Article[] articles) {
        articleTable.removeAllViews();
        articleTable.addView(articleTableRowSample);

        String text[] = new String[8];
        for (int i = 0; i < articles.length; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(articleTableRowSample.getLayoutParams());

            text[0] = Integer.toString(articles[i].getArticleNumber());
            text[1] = articles[i].getTitle();
            text[3] = articles[i].getWriter();
            text[2] = articles[i].getId();
            text[4] = articles[i].getContent();
            text[5] = articles[i].getWriteDate();
            text[6] = articles[i].getImgName();
            text[7] = Integer.toString(articles[i].getHits());

            Log.i("test", text[0] + text[1]);

            TextView tv[] = new TextView[8];
            for (int j = 0; j < 8; j++) {
                tv[j] = new TextView(this);
                tv[j].setLayoutParams(textViewSample.getLayoutParams());
                tv[j].setText(text[j]);
                tv[j].setBackgroundColor(Color.WHITE);
                tv[j].setGravity(Gravity.CENTER);
                tr.addView(tv[j]);
            }
            tr.setBackgroundColor(Color.WHITE);

            Button deleteBtn = new Button(this);
            deleteBtn.setText(text[0]);
            deleteBtn.setTextColor(Color.GRAY);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    proxy.admin_article_delete(((Button) v).getText().toString());
                    proxy.admin_article_get();
                }
            });
            tr.addView(deleteBtn);

            Button updateBtn = new Button(this);
            deleteBtn.setText(text[0]);
            deleteBtn.setTextColor(Color.LTGRAY);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            tr.addView(updateBtn);
            articleTable.addView(tr);
        }
    }
}