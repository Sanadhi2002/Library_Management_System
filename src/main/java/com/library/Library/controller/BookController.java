package com.library.Library.controller;


import com.library.Library.entity.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.stream.Collectors;
import com.library.Library.repository.UserRepository;
import com.library.Library.service.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService service;


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

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/process_register")
    public  String processRegister(User user){

        Role defaultRole = entityManager.find(Role.class, 2);

        // Check if the role with ID 2 exists
        if (defaultRole == null) {
            // Handle the case where the role with ID 2 does not exist
            return "register";
        }

        // Set the default role for the user
        user.setRole(defaultRole);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return "register_success";
    }

    @PostMapping("/saveUser")
    public  String addUser( User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return  "redirect:/users";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/users")
    public String listUsers(Model model,@Param("keyword") String keyword){
        List<User> listUsers =userDetailsService.listAll(keyword);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("keyword", keyword);
        return "users";
    }
    @RequestMapping("/available_books")
    public String getAllBook(Model model, @Param("keyword") String keyword){
        List<Book> list=service.listAll(keyword);
        model.addAttribute("book",list);
        model.addAttribute("keyword", keyword);

        return "bookList";
    }




    @GetMapping("/new_member")
    public String memberRegister(){
        return "memberRegister";
    }


    @GetMapping("/book_register")
    public String bookRegister(){
        return "bookRegister";
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute Book b, @RequestParam("image") MultipartFile imageFile) {
        try {
            String imagePath = "/" + imageFile.getOriginalFilename();
            Files.write(Paths.get(imagePath), imageFile.getBytes());


            b.setImageURL(imagePath);


            service.save(b);

            return "redirect:/available_books";
        } catch (IOException e) {

            e.printStackTrace();
            return "error";
        }
    }





    @GetMapping("/books")
    public ModelAndView getBooks(){
        List<Book> list=service.getAllBook();
        List<Book> availableBooks = list.stream()
                .filter(book -> book.getCount() > 0)
                .collect(Collectors.toList());

        return new ModelAndView("gallery","book",availableBooks);

    }

    @GetMapping("/members")
    public ModelAndView getAllMembers(){
        List<Member> memberList=memberService.getAllMembers();
        return  new ModelAndView("memberList","member",memberList);
    }


    @PostMapping("/saveMember")
    public  String addMember(@ModelAttribute Member member){
        memberService.saveMember(member);
        return  "redirect:/new_member";
    }



    @RequestMapping("/editBook/{id}")
    public String editBook(@PathVariable("id") int id, Model model){
        Book b =service.getBookById(id);
        model.addAttribute("book",b);
        return "bookEdit";
    }

    @RequestMapping("/editMember/{id}")
    public String editMember(@PathVariable("id") int id, Model model){
        Member m=memberService.getMemberById(id);

        model.addAttribute("member",m);
        return "memberEdit";
    }

    @RequestMapping("/editUser/{id}")
    public String editUser(@PathVariable("id") int id, Model model){
        User u = userDetailsService.getUserById(id);


        model.addAttribute("user",u);
        return "userEdit";
    }


    @RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") int id){

        service.deleteById(id);
        return "redirect:/available_books";
    }

    @RequestMapping("/deleteMember/{id}")
    public String deleteMember(@PathVariable("id")int id){
        memberService.deleteById(id);
        return "redirect:/new_member";
    }

    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id")int id){
        userDetailsService.deleteById(id);
        return "redirect:/new_member";
    }

    @GetMapping("/logout")
    public String logout(Model model){

        String userEmail = userDetailsService.getCurrentUser();

        if (userEmail != null) {
            model.addAttribute("userEmail", userEmail);
            return  userDetailsService.LogoutUser();
        } else {
            // Handle the case where the user is not authenticated
            return "redirect:/home"; // Redirect to the login page or another appropriate action
        }

    }

    @RequestMapping("/profile")
    public String getProfilePage(){

        return "profile";
    }
    @GetMapping("/profile")
    public String userProfile(Model model){
        String userEmail = userDetailsService.getCurrentUser();

        if (userEmail != null) {
            model.addAttribute("userEmail", userEmail);
            return "profile";
        } else {
            // Handle the case where the user is not authenticated
            return "redirect:/"; // Redirect to the login page or another appropriate action
        }


    }

    @PostMapping("/borrowBook/{id}")
    public String borrowBook(@PathVariable("id") int id){
        Book b = service.getBookById(id);
        if (b.getCount() > 0) {
            b.setCount(b.getCount() - 1);
            service.save(b);
            User currentUser = userDetailsService.getCurrentUserEntity();
            BorrowedBook borrowedBook = new BorrowedBook(currentUser, b, false);
            borrowedBookService.save(borrowedBook);

        }else{
            System.out.println("boorrow failed");
        }
        return "redirect:/books";
    }




    @GetMapping("/borrowed_books")
    public ModelAndView BorrowedBooksByUser(Model model){

        User currentUser=userDetailsService.getCurrentUserEntity();
        List<BorrowedBook> borrowedBooks = borrowedBookService.findByUserAndIsReturnedFalse(currentUser);

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
            borrowedBook.setReturned(true);
            borrowedBookService.save(borrowedBook);
            //borrowedBookService.delete(borrowedBook);

        }else {
            System.out.println("Return failed");
        }
        return "redirect:/borrowed_books";


    }


}
