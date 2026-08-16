package com.codeWithAyshah.usersmanagment.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

@Data
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "fullname")
    private String fullName;
    @Column(name = "phonenumber")
    private String phoneNumber;
    @Column(name = "deleted")
    private boolean deleted;


    @OneToMany(mappedBy = "user" ,cascade =CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<UserAddresses> addresses;

    public User() {

    }

    public User(int id, String fullName, String phoneNumber, boolean deleted) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.deleted = deleted;
    }
    public void addAddress(UserAddresses address) {
        addresses.add(address);
        address.setUser(this);
    }

    public void removeAddress(UserAddresses address) {
        addresses.remove(address);
        address.setUser(null);
    }

}
