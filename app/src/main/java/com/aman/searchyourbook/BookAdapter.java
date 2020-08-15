package com.aman.searchyourbook;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.List;



public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    public static final String TAG=BookAdapter.class.getSimpleName();

    private List<BookItem> bookList;
    private Context context;


    public BookAdapter(List<BookItem> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookItem book = bookList.get(position);
        holder.textViewTitle.setText(MessageFormat.format("Title: {0}", book.getTitle()));
        holder.textViewAuthor.setText(MessageFormat.format("Author: {0}", book.getAuthor()));
        holder.textViewPublication.setText(MessageFormat.format("Publisher: {0}", book.getPublisher()));

        //Picasso.get().load(book.getImageResource()).fit().centerInside().into(holder.imageView);
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.d("BookAdapter", "onImageLoadFailed: "+exception.getMessage());
                Toast.makeText(context, "Error"+exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.build().load(book.getImageResource()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+bookList.size());
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle;
        public TextView textViewAuthor;
        public TextView textViewPublication;
        public ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.txtTitle);
            textViewAuthor = itemView.findViewById(R.id.txtAuthor);
            textViewPublication = itemView.findViewById(R.id.txtPublisher);
            imageView=itemView.findViewById(R.id.imageView);


        }


    }
}
