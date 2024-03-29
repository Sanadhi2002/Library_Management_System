package com.library.Library.repository;


import com.library.Library.entity.Book;
import com.library.Library.entity.BorrowedBook;
import com.library.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Integer>{

    List<BorrowedBook> findByUser(User user);

    BorrowedBook findByUserAndBook(User user, Book book);

    List<BorrowedBook> findByUserAndIsReturnedFalse(User user);

    BorrowedBook findByIdAndIsReturnedFalse(int id);

    @Query("SELECT b FROM BorrowedBook b WHERE CONCAT(b.user.email, b.book.name, b.book.author) LIKE %?1%")
    List<BorrowedBook> findByKeyword(String keyword);
}
