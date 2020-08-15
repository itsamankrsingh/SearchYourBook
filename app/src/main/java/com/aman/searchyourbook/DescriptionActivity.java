package com.aman.searchyourbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.aman.searchyourbook.MainActivity.BOOK_AUTHOR;
import static com.aman.searchyourbook.MainActivity.BOOK_DESCRIPTION;
import static com.aman.searchyourbook.MainActivity.BOOK_IMAGE_RESOURCE;
import static com.aman.searchyourbook.MainActivity.BOOK_TITLE;


public class DescriptionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent=getIntent();

        String title=intent.getStringExtra(BOOK_TITLE);

        //change title of the app
        getSupportActionBar().setTitle(title);

        String author=intent.getStringExtra(BOOK_AUTHOR);
        String description=intent.getStringExtra(BOOK_DESCRIPTION);
        String imageResource=intent.getStringExtra(BOOK_IMAGE_RESOURCE);

        TextView textViewTitle=findViewById(R.id.text_view_description_title);
        TextView textViewAuthor=findViewById(R.id.text_view_description_author);
        TextView textViewDescription=findViewById(R.id.text_view_description);
        ImageView imageView=findViewById(R.id.image_view_description);

        textViewDescription.setText(description);
        textViewTitle.setText(title);
        textViewAuthor.setText(author);


        Picasso.get().load(imageResource).fit().centerInside().into(imageView);



    }
}