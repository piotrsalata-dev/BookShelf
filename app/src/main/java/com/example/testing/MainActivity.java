package com.example.testing;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextAuthor;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference booksRef = db.collection("Book");
    private DocumentReference noteRef = db.document("Books/My First Book");

    public void openBookListActivity(){
        Intent intent = new Intent(this, Bookshelf.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.add_title);
        editTextAuthor = findViewById(R.id.add_author);


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

    }

    public void addBook(View v) {
        String title = editTextTitle.getText().toString();
        String author = editTextAuthor.getText().toString();
        Book book = new Book(title, author);
        booksRef.add(book);

        editTextTitle.setText("");
        editTextAuthor.setText("");
    }

    public void clickShow(View view) {
        RecyclerView recyclerView;
        FirebaseFirestore db;
        final List<Book> filmList;
        final BookAdapter adapter;

        db = FirebaseFirestore.getInstance();
        filmList = new ArrayList<>();
        adapter = new BookAdapter(this, filmList);
        recyclerView = findViewById(R.id.book_shelf);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        db.collection("Book").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            Toast.makeText(MainActivity.this, "Firebase loaded", Toast.LENGTH_LONG).show();

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for(DocumentSnapshot d : list){

                                Book p = d.toObject(Book.class);
                                assert p != null;
                                p.setDocumentID(d.getId());
                                filmList.add(p);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}