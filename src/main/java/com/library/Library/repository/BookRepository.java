package com.library.Library.repository;


import com.library.Library.entity.Book;
import com.library.Library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByNameContainingOrAuthorContaining(String name, String author);

}
