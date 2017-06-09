package com.example.android.newsyapp;

/**
 * Created by irosaro on 9/4/16.
 */
public class Article {

    // Strings for the news article
    private String mArticleTitle;

    private String mArticleSection;

    private String mArticleUrl;

    private String mArticleImage;

    private String mArticleAuthor;

    // custom constructor for adding news article object
    public Article(String title, String section, String url, String img, String author) {
        mArticleTitle = title;
        mArticleSection = section;
        mArticleUrl = url;
        mArticleImage = img;
        mArticleAuthor = author;
    }

    // news article info get methods
    public String getArticleTitle() {
        return mArticleTitle;
    }

    public String getArticleSection() {
        return mArticleSection;
    }

    public String getArticleUrl() {
        return mArticleUrl;
    }

    public String getArticleImage() {
        return mArticleImage;
    }

    public String getArticleAuthor() {
        return mArticleAuthor;
    }
}
