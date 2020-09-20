package com.example.testing;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Book implements Serializable {

    private String title;
    private String author;
    @Exclude private String documentID;

    public Book() {
        //public no-arg constructor needed
    }

    @Exclude
    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public Book(String title){
        this.title = title;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;

    }

    public  String getTitle() {
        return title;
    }
    public  String getAuthor() {
        return author;
    }

}
