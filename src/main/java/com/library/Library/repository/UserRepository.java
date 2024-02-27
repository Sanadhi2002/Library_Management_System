package com.library.Library.repository;

import com.library.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = ?1")
    public User findUserByEmailWithRole( String email);
}
