package com.library.Library.repository;

import com.library.Library.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

    @Query("SELECT c FROM Category c WHERE c.id = ?1 OR c.category_name = ?2")
    public  Category findByIdOrCategory_name(int id, String category_name);



    @Query("SELECT c FROM Category c WHERE CONCAT(c.id, c.category_name) LIKE %?1%")
    List<Category> findByKeyword(String keyword);

    @Query("SELECT c FROM Category c WHERE c.category_name = ?1")
    public Category findByCategory_name(String category_name);

    @Query("SELECT c FROM Category c WHERE c.id = ?1")
    public Category findById(int id);



}
