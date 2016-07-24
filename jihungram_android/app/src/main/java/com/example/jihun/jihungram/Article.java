package com.example.jihun.jihungram;

/**
 * Created by jihun on 2016-07-17.
 */
public class Article {
    private int articleNumber;
    private String title;
    private String writer;
    private String id;
    private String content;
    private String writeDate;
    private String imgName;
    private int hits;

    public Article(int articleNumber, String title, String writer, String id, String content, String writeDate, String imgName, int hits) {
        this.articleNumber = articleNumber;
        this.title = title;
        this.writer = writer;
        this.id = id;
        this.content = content;
        this.writeDate = writeDate;
        this.imgName = imgName;
        this.hits = hits;
    }

    public int getArticleNumber() {
        return articleNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public String getImgName() {
        return imgName;
    }

    public int getHits() { return hits; }
}
