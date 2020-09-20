package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Bookshelf extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);

        recyclerView =(RecyclerView)findViewById(R.id.book_shelf);
    }

    public void clickShow(View view) {
        RecyclerView recyclerView;
        FirebaseFirestore db;
        final List<Book> bookShelf;
        final BookAdapter adapter;

        db = FirebaseFirestore.getInstance();
        bookShelf = new ArrayList<>();
        adapter = new BookAdapter(this, bookShelf);
        recyclerView = findViewById(R.id.book_shelf);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        db.collection("Book").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            Toast.makeText(Bookshelf.this, "Database loaded", Toast.LENGTH_LONG).show();

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for(DocumentSnapshot d : list){
                                Book p = d.toObject(Book.class);
                                assert p != null;
                                p.setDocumentID(d.getId());
                                bookShelf.add(p);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}