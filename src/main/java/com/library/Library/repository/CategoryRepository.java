package com.library.Library.repository;

import com.library.Library.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

    @Query("SELECT c FROM Category c WHERE c.id = ?1 OR c.category_name = ?2")
    public  Category findByIdOrCategory_name(int id, String category_name);

}
