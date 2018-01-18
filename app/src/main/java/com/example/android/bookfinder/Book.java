package com.example.android.bookfinder;

/**
 * Created by raylinares on 1/17/18.
 */

/**
 * {@link Book} represents an Book object which consists of the title of the book, the author of the book,
 * a description of the book, a URL link for more info on the book, and a URL link to a thumbnail image of the book cover
 * to display in the app.
 */
public class Book {
    /**Title of the book**/
    private String mBookTitle;

    /**Author of the book**/
    private String mAuthor;

    /**Description of the book**/
    private String mDescription;

    /**URL link for more info on book**/
    private String mInfoLinkURL;

    /**URL link to a thumbnail image of the book cover**/
    private String mThumbnailImageURL;

    /**
     * Create a new Book object
     * @param bookTitle is the Title of the book.
     *
     * @param author is the Author of the book.
     *
     * @param description is the Description of the book.
     *
     * @param infoLinkURL is the URL link for more info on book.
     *
     * @param thumbnailImageURL is the URL link to a thumbnail image of the book cover.
     */
    public Book(String bookTitle, String author, String description, String infoLinkURL, String thumbnailImageURL ){
        mBookTitle = bookTitle;
        mAuthor = author;
        mDescription = description;
        mInfoLinkURL = infoLinkURL;
        mThumbnailImageURL = thumbnailImageURL;
    }
    public String getBookTitle(){
        return mBookTitle;
    }
    public String getAuthor(){
        return mAuthor;
    }
    public String getDescription(){
        return mDescription;
    }
    public String getInfoLinkURL(){
        return mInfoLinkURL;
    }
    public String getThumbnailImageURL(){
        return mThumbnailImageURL;
    }
}
