package com.example.Dobara1.usermaster;

import jakarta.persistence.*;

import javax.management.relation.Role;

@Entity
@Table(name="user_master")
public class UserMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name="user_id")
    int id;

    String name , email , address , password , contact;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role{
    ADMIN, USER
    }
    byte [] profile_pic;

    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "user_id", insertable = false , updatable = false)   //
    private UserMasterEntity user;


    public UserMasterEntity(int id, String name, String email, String address, String password, String contact, Role role, byte[] profile_pic) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.contact = contact;
        this.role = role;
        this.profile_pic = profile_pic;
    }

    public UserMasterEntity() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public byte[] getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(byte[] profile_pic) {
        this.profile_pic = profile_pic;
    }
}
