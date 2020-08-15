package com.aman.searchyourbook;

public class BookItem {
    private String mTitle;
    private String mAuthor;
    private String mPublisher;
    private String mImageResource;

    public BookItem(String mTitle, String mAuthor, String mPublisher) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mPublisher = mPublisher;
    }
    public BookItem(String mTitle, String mAuthor, String mPublisher,String imageResource) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mPublisher = mPublisher;
        this.mImageResource=imageResource;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getImageResource() {
        return mImageResource;
    }
}
