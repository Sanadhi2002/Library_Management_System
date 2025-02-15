package com.library.Library.service;

import com.library.Library.entity.*;
import com.library.Library.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @PersistenceContext
    EntityManager entityManager;


    public void saveUser(User user){
        userRepo.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmailWithRole(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user.getRole())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public User getUserById(long id){

        return userRepo.findById(id).get();
    }
    public void deleteById(long id){
        userRepo.deleteById(id);
    }





    public String getCurrentUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(authentication!=null&& authentication.isAuthenticated()){
            return authentication.getName();
        }

        return null;
    }

    public String LogoutUser(){
        SecurityContextHolder.clearContext();
        entityManager.clear();
        return "redirect:/home";
    }

    public User getCurrentUserEntity() {
    String email = getCurrentUser();
            User user= userRepo.findUserByEmailWithRole(email);
            return user;

    }

    public List<User> listAll(String keyword) {
        if (keyword != null) {
            return userRepo.findByKeyword(keyword);
        }
        return userRepo.findAll();
    }

    public List<User> searchUser(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepo.findAll();
        }
        return userRepo.findByKeyword(keyword);
    }


}