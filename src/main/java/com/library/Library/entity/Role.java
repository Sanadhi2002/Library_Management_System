package com.library.Library.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "role_table")
public class Role implements GrantedAuthority {

    @Id
    private int id;
    private  String name;

    @OneToMany(mappedBy = "role")
    private Set<User>users;

    public Role(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public  Role(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
