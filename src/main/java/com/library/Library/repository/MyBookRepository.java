package com.library.Library.repository;

import com.library.Library.entity.MyBookList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MyBookRepository extends JpaRepository<MyBookList, Integer> {
}
