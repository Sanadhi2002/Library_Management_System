package com.library.Library.service;


import com.library.Library.entity.Book;
import com.library.Library.entity.BorrowedBook;
import com.library.Library.entity.User;
import com.library.Library.repository.BorrowedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowedBookService {
    @Autowired
    private BorrowedBookRepository bRepo;


    public void save(BorrowedBook borrowedBook) {
        bRepo.save(borrowedBook);
    }

    public List<BorrowedBook> getBorrowedBooksByUser(User user){
        return bRepo.findByUser (user);
    }

    public List<BorrowedBook> displayAllBorrowedBooks(){
        return bRepo.findAll();
    }

    public void delete(BorrowedBook borrowedBook) {

        bRepo.delete(borrowedBook);
    }

    public BorrowedBook findByUserAndBook(User user, Book book) {
        return bRepo.findByUserAndBook(user, book);
    }



    public BorrowedBook findById(int id) {
        return bRepo.findByIdAndIsReturnedFalse(id);
    }

    public List<BorrowedBook> findByUserAndIsReturnedFalse(User currentUser) {
        return bRepo.findByUserAndIsReturnedFalse(currentUser);
    }

    public List<BorrowedBook> listAll(String keyword) {
        if (keyword != null) {
            return bRepo.findByKeyword(keyword);
        }
        return bRepo.findAll();
    }
}
