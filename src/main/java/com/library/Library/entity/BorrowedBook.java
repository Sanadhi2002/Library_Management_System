package com.library.Library.entity;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class BorrowedBook  implements  Charges{

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

    @Column(nullable = false)
    private LocalDate returnDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private  double fine;

    @Column(nullable = false)
    private  boolean fineIsPayed = false;


    @Column(nullable = false, columnDefinition = "boolean default false")
    private  boolean isReturned = false;

    public BorrowedBook(int id, User user, Book book, LocalDate borrowDate, LocalDate  returnDate, LocalDate dueDate, double fine, boolean fineIsPayed, boolean isReturned) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.fine = fine;
        this.fineIsPayed = fineIsPayed;
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

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getDueDate() {
        dueDate= borrowDate.plusDays(14);
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public boolean isFineIsPayed() {
        return fineIsPayed;
    }

    public void setFineIsPayed(boolean fineIsPayed) {
        this.fineIsPayed = fineIsPayed;
    }

    @Override
    public void calculateBill() {
        if (LocalDate.now().isAfter(dueDate)) {
            long days = LocalDate.now().toEpochDay() - dueDate.toEpochDay();
            double fine = days * 100;
            System.out.println("Fine for the book is: LKR " + fine);
        } else {
            this.fine = 0;
            System.out.println("No fine for the book");
        }

    }
}
