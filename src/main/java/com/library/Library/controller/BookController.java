package com.library.Library.controller;


import com.library.Library.entity.Book;
import com.library.Library.entity.Member;
import com.library.Library.entity.MyBookList;
import com.library.Library.service.BookService;
import com.library.Library.service.MemberService;
import com.library.Library.service.MyBookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private MyBookListService myBookService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/")
    public String home(){
        return "home";
    }



    @GetMapping("/new_member")
    public String memberRegister(){
        return "memberRegister";
    }



    @GetMapping("/book_register")
    public String bookRegister(){
        return "bookRegister";
    }

    @GetMapping("/available_books")
    public ModelAndView getAllBook(){
        List<Book> list=service.getAllBook();
        // ModelAndView  m=new ModelAndView();
        // m.setViewName("bookList");
        // m.addObject("book",list);
        return new ModelAndView("bookList","book",list);
    }

    @GetMapping("/members")
    public ModelAndView getAllMembers(){
        List<Member> memberList=memberService.getAllMembers();
        return  new ModelAndView("memberList","member",memberList);
    }



    @PostMapping("/save")
    public String addBook(@ModelAttribute Book b){
        service.save(b);
        return "redirect:/available_books";
    }

    @PostMapping("/saveMember")
    public  String addMember(@ModelAttribute Member member){
        memberService.saveMember(member);
        return  "redirect:/new_member";
    }

    @GetMapping("/my_books")
    public String getMyBooks(Model model){
        List<MyBookList> list=myBookService.getAllMyBooks();
        model.addAttribute("book",list);
        return "myBooks";
    }

    @RequestMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id") int id) {
        Book b = service.getBookById(id);
        MyBookList mb = new MyBookList(b.getId(), b.getName(), b.getAuthor(), b.getPrice());
        myBookService.saveMyBooks(mb);
        return "redirect:/my_books";
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














}