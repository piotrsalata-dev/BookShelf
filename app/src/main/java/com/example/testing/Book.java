package com.example.testing;

import com.google.firebase.firestore.Exclude;

public class Book {
    private String title;
    private String author;
    private String comment;
    private String documentID;

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

    public Book(String title, String author, String comment) {
        this.title = title;
        this.author = author;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getComment() {
        return comment;
    }
}
