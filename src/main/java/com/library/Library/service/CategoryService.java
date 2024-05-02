package com.library.Library.service;


import com.library.Library.entity.Book;
import com.library.Library.entity.Category;
import com.library.Library.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository cRepo;

    public List<Category> getAllCategories(){

        return cRepo.findAll();
    }

    public void save(Category c){
        cRepo.save(c);

    }

    public Category getCategoryById(int id){
        return cRepo.findById(id).get();
    }

    public  void deleteById(int id){
        cRepo.deleteById(id);
    }





}
