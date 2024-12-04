//package hua.project.Entities;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "users")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column
//    private int id;
//
//    @Column
//    private String username;
//
//    @Column
//    private String password;
//
//    @Column
//    private String email;
//
//    @Column
//    @Enumerated(EnumType.STRING)
//    private Role role; // OWNER, TENANT
//
//
//    public User(int id, String username, String password, String email, Role role) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.role = role;
//    }
//
//    public User() {
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//}