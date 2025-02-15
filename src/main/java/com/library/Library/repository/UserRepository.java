package com.library.Library.repository;


import com.library.Library.entity.Book;
import com.library.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = ?1")
    public User findUserByEmailWithRole( String email);
    @Query("SELECT u FROM User u WHERE " +
            "LOWER(CONCAT(u.email, ' ', u.first_name, ' ', u.last_name, ' ', u.phone)) " +
            "LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> findByKeyword(String keyword);

    Optional<User> findById(Long id);
}
