package com.library.Library.controller;


import com.library.Library.entity.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import com.library.Library.repository.UserRepository;
import com.library.Library.service.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private BorrowedBookService borrowedBookService;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/fragments")
    public String fragments(){
        return "navbar.html";
    }

    @GetMapping("/categories")
    public String categories(Model model){

        List<Category> listCategories=categoryService.getAllCategories();
        model.addAttribute("listCategories",listCategories);
        return "categories";
    }


    @GetMapping("/books")
    public String listBooks(Model model, @RequestParam(value = "keyword", required = false)String keyword) {
        List<Book> listBooks = (keyword != null && !keyword.isEmpty()) ? service.searchBooks(keyword) : service.getAllBook();
        List<Category> categories = categoryService.getAllCategories();


        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("listBooks", listBooks);
        model.addAttribute("categories", categories);
        model.addAttribute("keyword", keyword);

        return "books";
    }



    @GetMapping("/available_books")
    public String listAvailableBooks(Model model,@RequestParam(value = "keyword", required = false)String keyword) {
        List<Book> listBooks = (keyword != null && !keyword.isEmpty()) ? service.searchBooks(keyword) : service.getAllBook();
        List<Category> categories = categoryService.getAllCategories();

        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("listBooks", listBooks);
        model.addAttribute("categories", categories);
        model.addAttribute("keyword", keyword);

        return "bookList";
    }


    @GetMapping("/available_books/category/{categoryId}")
    public String listBooksByCategoryinBookList(@PathVariable("categoryId") Integer categoryId, Model model) {
        List<Book> listBooks;
        List<Category> categories = categoryService.getAllCategories();

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            if (category != null) {
                listBooks = service.getBooksByCategory(category);
                if (listBooks.isEmpty()) {
                    model.addAttribute("noBooks", true);
                    model.addAttribute("categoryName", category.getCategory_name());
                }
            } else {
                listBooks = service.getAllBook();
            }
        } else {
            listBooks = service.getAllBook();
        }

        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("listBooks", listBooks);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", categoryId);

        return "bookList";
    }


    @GetMapping("/books/category/{categoryId}")
    public String listBooksByCategory(@PathVariable("categoryId") Integer categoryId, Model model) {
        List<Book> listBooks;
        List<Category> categories = categoryService.getAllCategories();

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            if (category != null) {
                listBooks = service.getBooksByCategory(category);
                if (listBooks.isEmpty()) {
                    model.addAttribute("noBooks", true);
                    model.addAttribute("categoryName", category.getCategory_name());
                }
            } else {
                listBooks = service.getAllBook();
            }
        } else {
            listBooks = service.getAllBook();
        }

        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("listBooks", listBooks);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", categoryId);

        return "books";
    }




    @GetMapping("/bookRegister")
    public String getBooks(Model model) {
        Book book = new Book();
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("book", book);
        model.addAttribute("categories", categories);
        return "bookRegister";
    }

    @PostMapping("/book_register")
    public String registerBook(@ModelAttribute Book book, @RequestParam("image") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            Path uploadPath = Path.of(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = imageFile.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


            book.setImageUrl("/uploads/" + uniqueFilename);
        }


        if (book.getCategory() != null && book.getCategory().getId() > 0) {
            Category category = categoryService.getCategoryById(book.getCategory().getId());
            if (category != null) {
                book.setCategory(category);
            }
        }

        service.save(book);
        return "redirect:/books";
    }


    @RequestMapping("/editBook/{id}")
    public String editBook(@PathVariable("id") int id, Model model){
        Book book =service.getBookById(id);
        model.addAttribute("book",book);

        List<Category> listCategories=categoryService.getAllCategories();
        model.addAttribute("listCategories",listCategories);
        return "bookEdit";
    }

    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute Book book, @RequestParam("image") MultipartFile imageFile) throws IOException {
       Book existingBook= service.getBookById(book.getId());


        if (existingBook == null) {
            return "redirect:/available_books";
        }


        if (!imageFile.isEmpty()) {

            Path uploadPath = Path.of(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }


            String originalFilename = imageFile.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;


            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


            book.setImageUrl("/uploads/" + uniqueFilename);
        }


        if (book.getCategory() != null && book.getCategory().getId() > 0) {
            Category category = categoryService.getCategoryById(book.getCategory().getId());
            if (category != null) {
                book.setCategory(category);
            }
        }
        existingBook.setName(book.getName());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setCount(book.getCount());
        existingBook.setImageUrl(book.getImageUrl());


        // Save the book
        service.update(existingBook);
        return "redirect:/books";
    }

    @RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") int id){

        service.deleteById(id);
        return "redirect:/available_books";
    }


    @PostMapping("/borrowBook/{id}")
    public String borrowBook(@PathVariable("id") int id) {
        Book b = service.getBookById(id);

        if (b.getCount() > 0) {

            b.setCount(b.getCount() - 1);
            service.save(b);

            User currentUser = userDetailsService.getCurrentUserEntity();


            BorrowedBook borrowedBook = new BorrowedBook(
                    id,                 // ID (auto-generated),
                    currentUser,        // User borrowing the book
                    b,                  // Book being borrowed
                    LocalDate.now(),    // Borrow date (current date)
                    LocalDate.now().plusDays(7),  // Return date (7 days from borrow date)
                    LocalDate.now().plusDays(7),  // Due date (7 days from borrow date)
                    0,                  // Fine (initially 0)
                    false,              // Fine is not paid initially
                    false               // Book is not returned initially
            );

            borrowedBookService.save(borrowedBook);

            return "redirect:/books";
        } else {
            System.out.println("Borrow failed: Book is currently unavailable.");
            return "redirect:/books?error=BookUnvailable";  // Optionally, pass an error flag or message to the view
        }
    }



    @GetMapping("/borrowed_books")
    public ModelAndView BorrowedBooksByUser(Model model){
        User currentUser=userDetailsService.getCurrentUserEntity();
        List<BorrowedBook> borrowedBooks = borrowedBookService.findByUserAndIsReturnedFalse(currentUser);
        if (borrowedBooks.isEmpty()) {
            model.addAttribute("noBooks", true);
        }

        model.addAttribute("borrowedBooks", borrowedBooks);
        return new ModelAndView("BooksBorrowed","borrowedBooks",borrowedBooks);
    }

    @RequestMapping("/all_borrowed_books")
    public String  DisplayAllBorrowedBooks(Model model,@Param("keyword") String keyword){
        List<BorrowedBook> borrowedBooks=borrowedBookService.listAll(keyword);
        model.addAttribute("borrowedBooks", borrowedBooks);
        model.addAttribute("keyword", keyword);
        return "allBorrowedBooks";

    }

    @PostMapping("/returnBook/{id}")
    public String returnBook(@PathVariable("id") int id) {

        BorrowedBook borrowedBook= borrowedBookService.findById(id);

        if(borrowedBook!=null){
            Book book=borrowedBook.getBook();
            book.setCount(book.getCount()+1);

            service.save(book);
            if (borrowedBook.getDueDate().isAfter(borrowedBook.getDueDate())) {
                borrowedBook.setFine(100*(LocalDate.now().toEpochDay()-borrowedBook.getDueDate().toEpochDay()));
            }
            borrowedBook.setReturned(true);
            borrowedBook.setReturnDate(LocalDate.now());
            borrowedBookService.save(borrowedBook);
            //borrowedBookService.delete(borrowedBook);

        }else {
            System.out.println("Return failed");
        }
        return "redirect:/borrowed_books";


    }


}
