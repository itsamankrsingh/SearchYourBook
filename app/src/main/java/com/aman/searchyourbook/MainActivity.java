package com.aman.searchyourbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookAdapter.OnBookListener {

    public static final String BOOK_TITLE = "title";
    public static final String BOOK_AUTHOR = "author";
    public static final String BOOK_IMAGE_RESOURCE = "url";
    public static final String BOOK_DESCRIPTION = "description";
    public static final String BOOK_PUBLISHER = "publisher";

    private static final String TAG = "MainActivity";

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    private EditText mBookNameEditText;
    private Button mSearchButton;
    RequestQueue mRequestQueue;
    private ProgressBar mProgressBar;

    private ArrayList<BookItem> mBooks;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mBookNameEditText = findViewById(R.id.edtBookName);
        mProgressBar = findViewById(R.id.progressBar);
        mSearchButton = findViewById(R.id.btnSearch);
        mBooks = new ArrayList<>();
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookQuery = mBookNameEditText.getText().toString().trim();

                //checking if the edit text view is not empty
                if (TextUtils.isEmpty(bookQuery)) {
                    mBookNameEditText.setError("Book Name Required");
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    //Log.d(TAG, "onClick: "+bookQuery);
                    //appending search query to the base url
                    String queryUrl = BASE_URL + bookQuery;
                    //Log.d(TAG, "onClick: " + queryUrl);
                    mBooks.clear();
                    searchQuery(queryUrl);
                }
            }
        });

    }

    private void searchQuery(String queryUrl) {
        mRequestQueue = Volley.newRequestQueue(this);
        Log.d(TAG, "searchQuery: " + queryUrl);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                queryUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        //Log.d(TAG, "onResponse: " + response);
                        showData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,
                                "Error: " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                    }
                });
        mRequestQueue.add(jsonObjectRequest);
    }

    private void showData(JSONObject response) {
        String bookName = "";
        String bookAuthor = "";
        String bookPublisher = "";
        String bookDescription = "";
        String imageResource = "";
        try {
            JSONArray jsonArray = response.getJSONArray("items");
            Log.d(TAG, "showData: " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                JSONObject volumeObject = itemJsonObject.getJSONObject("volumeInfo");
                try {
                    bookName = volumeObject.getString("title");
                    bookPublisher = volumeObject.getString("publisher");
                    JSONArray authorsArray = volumeObject.getJSONArray("authors");
                    bookAuthor = authorsArray.getString(0);
                    bookDescription = volumeObject.getString("description");

                } catch (Exception e) {
                    Log.d(TAG, "showData: " + e.getMessage());
                }
                //put link string outside try catch of normal string
                JSONObject imageObject = volumeObject.getJSONObject("imageLinks");
                imageResource = imageObject.getString("thumbnail");
                mBooks.add(new BookItem(bookName, bookAuthor, bookPublisher, imageResource, bookDescription));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter = new BookAdapter(mBooks, MainActivity.this, this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void OnBookClick(int position) {
        Intent bookIntent = new Intent(MainActivity.this, DescriptionActivity.class);
        BookItem bookClicked = mBooks.get(position);
        bookIntent.putExtra(BOOK_TITLE, bookClicked.getTitle());
        bookIntent.putExtra(BOOK_AUTHOR, bookClicked.getAuthor());
        bookIntent.putExtra(BOOK_DESCRIPTION, bookClicked.getDescription());
        bookIntent.putExtra(BOOK_IMAGE_RESOURCE, bookClicked.getImageResource());

        startActivity(bookIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportActionBar().setTitle(R.string.app_name);
    }
}