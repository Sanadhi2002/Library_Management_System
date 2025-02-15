package com.library.Library.controller;

import com.library.Library.entity.Category;
import com.library.Library.entity.User;
import com.library.Library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @RequestMapping("/available_categories")
    public String  getCategories(Model model){
        List<Category> listCategories=categoryService.getAllCategories();
        model.addAttribute("listCategories",listCategories);
        return "categories";

    }
    @PostMapping("/saveCategory" )
    public  String addCategory(Category category){
        categoryService.save(category);
        return "redirect:/categories";

    }


    @PostMapping("/updateCategory" )
    public  String updateCategory(@ModelAttribute("category") Category category){

      Category existingCategory=categoryService.getCategoryById(category.getId());

        if (existingCategory == null) {
            return "redirect:/categories";
        }
        // Update other fields
        existingCategory.setCategory_name(category.getCategory_name());

        categoryService.save(existingCategory);

        return "redirect:/categories";
    }
    @RequestMapping("/editCategory/{id}")
    public String editCategory(@PathVariable("id") int id, Model model){
        Category category=categoryService.getCategoryById(id);
        model.addAttribute("category",category);
        return "EditCategory";
    }
    @RequestMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") int id){
        categoryService.deleteById(id);
        return "redirect:/categories";
    }


}
