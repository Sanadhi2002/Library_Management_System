package com.library.Library.controller;

import com.library.Library.entity.Member;
import com.library.Library.entity.Role;
import com.library.Library.entity.User;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {
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

        if (defaultRole == null) {
            return "register";
        }

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




    @RequestMapping("/editMember/{id}")
    public String editMember(@PathVariable("id") int id, Model model){
        Member m=memberService.getMemberById(id);

        model.addAttribute("member",m);
        return "memberEdit";
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

    @RequestMapping("/editUser/{id}")
    public String editUser(@PathVariable("id") int id, Model model){
        User u = userDetailsService.getUserById(id);


        model.addAttribute("user",u);
        return "userEdit";
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

            return "redirect:/home";
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
            return "redirect:/";
        }


    }
}
