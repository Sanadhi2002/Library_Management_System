package com.library.Library.repository;


import com.library.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = ?1")
    public User findUserByEmailWithRole( String email);

    @Query("SELECT u FROM User u WHERE u.first_name LIKE %?1% OR u.last_name LIKE %?1%  ")
    List<User> searchUsers(String keyword);

}
