package com.example.testing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private List<Book> bookShelf;


    public BookAdapter(Context co, List<Book> bookshelf) {
        this.context = co;
        this.bookShelf = bookshelf;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookViewHolder(
                LayoutInflater.from(context).inflate(R.layout.book, parent, false)
        );
    }

    @Override
    public int getItemCount() {
        return bookShelf.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        final Book book = bookShelf.get(position);
        final Context context = holder.deleteButton.getContext();
        holder.titleView.setText(book.getTitle());
        holder.authorView.setText(book.getAuthor());


        holder.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage(R.string.delete_question)
                        .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseFirestore db;
                                db = FirebaseFirestore.getInstance();
                                db.collection("Book").document(book.getDocumentID()).delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_LONG).show();
                                                    notifyDataSetChanged();
                                                }
                                            }
                                        });
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, null)
                        .show();
            }
        });

    }

    class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titleView;
        TextView authorView;
        ImageButton deleteButton;

        public BookViewHolder(View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.book_title);
            authorView = itemView.findViewById(R.id.book_author);
            deleteButton = itemView.findViewById(R.id.delete_button);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

    }
}

