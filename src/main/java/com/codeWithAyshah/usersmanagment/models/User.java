package com.codeWithAyshah.usersmanagment.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "fullname")
    private String fullName;
    @Column(name = "phonenumber")
    private String phoneNumber;
    @Column(name = "deleted")
    private boolean deleted;


    @OneToMany(mappedBy = "user" ,cascade =CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<UserAddresses> addresses=new ArrayList<>();

    public void addAddress(UserAddresses address) {
        addresses.add(address);
        address.setUser(this);
    }

    public void removeAddress(UserAddresses address) {
        addresses.remove(address);
        address.setUser(null);
    }

}
