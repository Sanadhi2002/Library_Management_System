package com.bookStop.BookShop.repository;

import com.bookStop.BookShop.entity.MyBookList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MyBookRepository extends JpaRepository<MyBookList, Integer> {
}
