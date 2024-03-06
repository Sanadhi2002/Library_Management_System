package com.library.Library.entity;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class BorrowedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private  int id;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @Column(nullable = false)
    private LocalDate borrowDate;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private  boolean isReturned = false;

    public BorrowedBook(User user, Book book, boolean isReturned) {
        this.user = user;
        this.book = book;
        this.borrowDate = LocalDate.now();
        this.isReturned = isReturned;
    }

    public BorrowedBook() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }


    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}
