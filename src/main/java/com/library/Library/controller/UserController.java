package com.library.Library.controller;

import com.library.Library.entity.*;
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
    public String listUsers(Model model,@RequestParam(value = "keyword", required = false) String keyword){
        List<User> listUsers =(keyword != null && !keyword.isEmpty()) ? userDetailsService.searchUser(keyword) : userDetailsService.getAllUsers();
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("keyword", keyword);
        return "users";
    }


    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        User existingUser = userDetailsService.getUserById(user.getId());

        if (existingUser == null) {
            return "redirect:/users";
        }


        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            existingUser.setPassword(encodedPassword);
        }

        // Update other fields
        existingUser.setFirst_name(user.getFirst_name());
        existingUser.setLast_name(user.getLast_name());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());


        userRepository.save(existingUser);

        return "redirect:/users";
    }

    @RequestMapping("/editUser/{id}")
    public String editUser(@PathVariable("id") int id, Model model){
        User u = userDetailsService.getUserById(id);


        model.addAttribute("user",u);
        return "userEdit";
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
