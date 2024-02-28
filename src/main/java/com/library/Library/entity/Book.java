package com.library.Library.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private  String name;




    private int count;
    private  String author;
    private  String price;



    @OneToMany(mappedBy = "book")
    private List<BorrowedBook> borrowedByMembers;



    public Book(int id, String name, String author, String price, int count,List<BorrowedBook> borrowedByMembers) {
        super();
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.count=count;
        this.borrowedByMembers = borrowedByMembers;

    }

    public Book(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<BorrowedBook> getBorrowedByMembers() {
        return borrowedByMembers;
    }

    public void setBorrowedByMembers(List<BorrowedBook> borrowedByMembers) {
        this.borrowedByMembers = borrowedByMembers;
    }


}
