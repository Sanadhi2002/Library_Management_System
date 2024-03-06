package com.library.Library.repository;


import com.library.Library.entity.Book;
import com.library.Library.entity.BorrowedBook;
import com.library.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Integer>{

    List<BorrowedBook> findByUser(User user);

    BorrowedBook findByUserAndBook(User user, Book book);
}
