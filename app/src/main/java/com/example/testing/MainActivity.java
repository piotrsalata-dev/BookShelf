package com.example.testing;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AddBookActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_COMMENT = "comment";

    private EditText editTextTitle;
    private EditText editTextAuthor;
    private EditText editTextComment;
    private TextView textViewData;
    private Button button;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference booksRef = db.collection("Book");
    private DocumentReference noteRef = db.document("Books/My First Book");

    public void openBookListActivity(){
        Intent intent = new Intent(this, BookList.class);
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.add_title);
        editTextAuthor = findViewById(R.id.add_author);
        editTextComment = findViewById(R.id.add_comment);
        //textViewData = findViewById(R.id.text_view_data);

        Button button = findViewById(R.id.book_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBookListActivity();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
        booksRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Book book = documentSnapshot.toObject(Book.class);
                    book.setDocumentID(documentSnapshot.getId());
                    
                    String documentId = book.getDocumentID();
                    String title = book.getTitle();
                    String author = book.getAuthor();
                    String comment = book.getComment();
                    
                    data += "ID: " + documentId + "\nTitle: " + title + "\nAuthor: " + author + "\nComment: " + comment + "\n\n";
                }
                textViewData.setText(data);
            }
        });

         */
    }

    public void addBook(View v) {
        String title = editTextTitle.getText().toString();
        String author = editTextAuthor.getText().toString();
        String comment = editTextComment.getText().toString();
        Book book = new Book(title, author, comment);
        booksRef.add(book);
    }

    public void loadBooks(View v) {
        booksRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = " ";

                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Book book = documentSnapshot.toObject(Book.class);
                            book.setDocumentID(documentSnapshot.getId());

                            String documentId = book.getDocumentID();
                            String title = book.getTitle();
                            String author = book.getAuthor();
                            String comment = book.getComment();

                            data += "ID: " + documentId + "\nTitle: " + title + "\nAuthor: " + author + "\nComment: " + comment + "\n\n";
                        }
                        textViewData.setText(data);
                    }
                });
    }
}