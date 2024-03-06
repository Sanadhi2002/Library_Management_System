package com.library.Library.service;


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
}
