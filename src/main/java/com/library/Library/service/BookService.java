package com.library.Library.service;

import com.library.Library.entity.Book;
import com.library.Library.entity.Category;
import com.library.Library.entity.Member;
import com.library.Library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bRepo;

    public void save(Book b){
        bRepo.save(b);

    }
    public List<Book> getAllBook(){return bRepo.findAll();}

    public Book getBookById(int id){
        return bRepo.findById(id).get();
    }

    public void deleteById(int id){
        bRepo.deleteById(id);
    }

    public List<Book> searchBooks(String keyword){
        return bRepo.findByNameContainingOrAuthorContaining(keyword, keyword);
    }

    public List<Book> listAll(String keyword) {
        if (keyword != null) {
            return bRepo.findByKeyword(keyword);
        }
        return bRepo.findAll();
    }

    public List<Book> getBooksByCategory(int categoryId) {
        return bRepo.findByCategoryId(categoryId);
    }

    public List<Book> getBooksByCategory(Category category) {
        return bRepo.findByCategory(category);
    }

    public List<Book> findByKeyword(String keyword) {
        return bRepo.findByNameContainingOrAuthorContaining(keyword, keyword);

    }
}
