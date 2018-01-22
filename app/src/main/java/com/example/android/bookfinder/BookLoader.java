package com.example.android.bookfinder;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by raylinares on 1/18/18.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    /** Query URL */
    private String mUrl;

public BookLoader(Context context, String url){
    super(context);
    mUrl = url;
}

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null){
            return null;
        }
        List<Book> bookList = QueryUtils.fetchBookData(mUrl);
        return bookList;
    }
}
