package com.example.android.bookfinder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by raylinares on 1/18/18.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Activity context, List<Book> books){
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Check if an existing view is being reused, otherwise inflate the view
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Get the {@link Earthquake} object located at this position in the list
        Book currentBook = getItem(position);

        //Get the current book title from the current Book object
        String bookTitle = currentBook.getBookTitle();
        // Find the TextView in the list_item.xml layout with the ID for the Book Title.
        TextView bookTitleTextView = (TextView) view.findViewById(R.id.book_title_view);
        bookTitleTextView.setText(bookTitle);

        //Get the current book author from the current Book object
        String bookAuthor = currentBook.getAuthor();
        // Find the TextView in the list_item.xml layout with the ID for the Book Author
        TextView bookAuthorTextView = (TextView) view.findViewById(R.id.book_author_view);
        bookAuthorTextView.setText(bookAuthor);

        //Get the current book description from the current Book object
        String bookDescription = currentBook.getDescription();
        // Find the TextView in the list_item.xml layout with the ID for the Book Description
        TextView bookDescriptionTextView = (TextView) view.findViewById(R.id.book_description_view);
        bookDescriptionTextView.setText(bookDescription);

        //Set placeholder image
        ImageView bookCoverImage = (ImageView) view.findViewById(R.id.book_cover_view);
        bookCoverImage.setImageResource(R.drawable.book_thumbnail_example);

        return view;
    }
}
