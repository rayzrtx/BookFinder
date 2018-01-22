package com.example.android.bookfinder;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by raylinares on 1/17/18.
 */

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private String searchValue;
    private String booksAPIURL = "https://www.googleapis.com/books/v1/volumes?q=" + searchValue + "+subject";

    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;

    /** Adapter for the list of books */
    private  BookAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        ListView bookListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_state_view);
        bookListView.setEmptyView(mEmptyStateTextView);


    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this, booksAPIURL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> bookList) {
        // Hide loading indicator because the data has been loaded
        ProgressBar spinner = (ProgressBar) findViewById(R.id.loading_spinner);
        spinner.setVisibility(View.GONE);

        // Set empty state text to display "No Books found."
        mEmptyStateTextView.setText(R.string.no_books);


        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        if (bookList == null){
            return;
        }
        // Update the information displayed to the user.
        mAdapter.addAll(bookList);

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
