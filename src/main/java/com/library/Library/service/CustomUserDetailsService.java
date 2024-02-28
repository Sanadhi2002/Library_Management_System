package com.library.Library.service;

import com.library.Library.entity.CustomUserDetails;
import com.library.Library.entity.Member;
import com.library.Library.entity.Role;
import com.library.Library.entity.User;
import com.library.Library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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



    public List<User> searchUsers(String keyword) {
        return userRepo.searchUsers(keyword);
    }



}