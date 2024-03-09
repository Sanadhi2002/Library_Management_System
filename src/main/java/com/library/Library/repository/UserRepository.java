package com.library.Library.repository;


import com.library.Library.entity.Book;
import com.library.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = ?1")
    public User findUserByEmailWithRole( String email);

    @Query("SELECT u FROM User u WHERE CONCAT(u.email, u.first_name, u.last_name, u.phone) LIKE %?1%")
    List<User> findByKeyword(String keyword);

}
