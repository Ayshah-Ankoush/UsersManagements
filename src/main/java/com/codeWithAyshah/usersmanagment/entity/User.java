package com.codeWithAyshah.usersmanagment.entity;

import jakarta.persistence.*;
import lombok.Data;

import static java.util.Arrays.stream;

@Data
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    @Column (name = "fullname")
    private String fullName ;
    @Column(name = "phonenumber")
    private String phoneNumber ;
    @Column(name = "address")
    private  String address;

    public  User(){

    }

    public User(int id, String fullName, String phoneNumber, String address) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

}
