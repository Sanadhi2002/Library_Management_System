package com.library.Library.repository;


import com.library.Library.entity.Book;
import com.library.Library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByNameContainingOrAuthorContaining(String name, String author);

    @Query("SELECT b FROM Book b WHERE CONCAT(b.name, b.author) LIKE %?1%")
    List<Book> findByKeyword(String keyword);
}
