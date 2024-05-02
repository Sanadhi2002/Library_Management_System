package com.library.Library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "category_table")
public class Category {

    @Id
    private int id;

    private String category_name;

    @OneToMany(mappedBy = "category")
    private Set<Book> books;

    public Category(int id, String category_name, Set<Book> books) {
        super();
        this.id = id;
        this.category_name = category_name;
        this.books = books;
    }

    public Category() {
        super();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
