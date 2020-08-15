package com.aman.searchyourbook;

public class BookItem {
    private String mTitle;
    private String mAuthor;
    private String mPublisher;
    private String mImageResource;
    private String mDescription;


    public BookItem(String mTitle, String mAuthor, String mPublisher, String imageResource) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mPublisher = mPublisher;
        this.mImageResource = imageResource;
    }

    public BookItem(String mTitle, String mAuthor, String mPublisher, String imageResource, String mDescription) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mPublisher = mPublisher;
        this.mImageResource = imageResource;
        this.mDescription = mDescription;
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

    public String getDescription() {
        return mDescription;
    }
}
