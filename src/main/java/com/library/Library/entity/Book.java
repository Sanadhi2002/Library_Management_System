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



    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BorrowedBook> borrowedBooks;

   @Column(name = "image_URL")
   private  String imageURL;

    @ManyToOne
    @JoinColumn(name = "category_id",  referencedColumnName = "id")
    private Category category;



    public Book(int id, String name, String author, String price, int count,List<BorrowedBook> borrowedBooks, String imageURL) {
        super();
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.count=count;
        this.borrowedBooks = borrowedBooks;
        this.imageURL= imageURL;

        Category defaultCategory = new Category();
        defaultCategory.setId(1);
        this.category = defaultCategory;

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

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
