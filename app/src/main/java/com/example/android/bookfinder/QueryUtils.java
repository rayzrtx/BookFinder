package com.example.android.bookfinder;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raylinares on 1/21/18.
 */

public class QueryUtils {
    /** Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Query the Google Books API and return a list of {@link Book} objects.
     */
    public static List<Book> fetchBookData(String requestURL){
        // Create URL object
        URL url = createUrl(requestURL);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Book}s
        List<Book> books = extractFeatureFromJSON(jsonResponse);

        //Return a list of Books
        return books;
    }

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL bookDataURL = null;
        try {
            bookDataURL = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return bookDataURL;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL bookDataURL) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (bookDataURL == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) bookDataURL.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Book> extractFeatureFromJSON(String bookJSON){
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookJSON)){
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Book> books = new ArrayList<>();

        // Try to parse the jsonResponse string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            //Create a JSONObject from the jsonResponse string
            JSONObject jsonRootObject = new JSONObject(bookJSON);

            //Extract the JSONArray associated with the key called "items"
            //which represents a list of items (or Books)
            JSONArray itemsArray = jsonRootObject.optJSONArray("items");

            //For each earthquake in the itemsArray, create an {@link Book} object
            for (int i = 0; i < itemsArray.length(); i++){
                //Get the JSON object in the items array at index i
                JSONObject bookObject = itemsArray.getJSONObject(i);
                //Get the volume info object in the book object
                JSONObject volumeInfoObject = bookObject.optJSONObject("volumeInfo");

                //Get the value of "title" from the volume info object
                String title = volumeInfoObject.getString("title");

                //Get the authors array from the volume info object
                JSONArray authorsArray = volumeInfoObject.optJSONArray("authors");
                //Get the value of authors array at index 0
                JSONObject authorsObject = authorsArray.getJSONObject(0);
                String author = authorsObject.toString();

                //Get the value of "Description" from volume info object
                String description = volumeInfoObject.getString("description");
                //Get the value of "infoLink" from volume info object
                String infoLink = volumeInfoObject.getString("infoLink");

                //Get the image links object from the volume info object
                JSONObject imagesObject = volumeInfoObject.optJSONObject("imageLinks");
                //Get the value of "smallThumbnail" from images object
                String bookThumbnailLink = imagesObject.getString("smallThumbnail");

                books.add(new Book(title, author, description, infoLink, bookThumbnailLink));
            }
        } catch (JSONException e){
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return books;
    }
}
