package com.library.Library.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true,length = 45)
    private  String  email;

    @Column(nullable = false,length = 65)
    private String password;

    @Column(name = "first_name", nullable = false, length = 20)
    private String first_name;

    @Column(name = "last_name", nullable = false, length = 20)
    private String last_name;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id",  referencedColumnName = "id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<BorrowedBook> borrowedBooks;




    public User(Long id, String email, String password, String first_name, String last_name, String phone,List<BorrowedBook> borrowedBooks) {
        super();
        this.id = id;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone=phone;
        this.borrowedBooks = borrowedBooks;

        Role defaultRole = new Role();
        defaultRole.setId(2);
        this.role = defaultRole;
    }



    public  User(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
