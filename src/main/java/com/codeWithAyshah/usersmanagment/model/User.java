package com.codeWithAyshah.usersmanagment.model;

import com.codeWithAyshah.usersmanagment.controller.addressDTO.CreateUserAddressRequest;
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
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "deleted")
    private boolean deleted;


    @OneToMany(mappedBy = "user" ,cascade =CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<UserAddress> addresses=new ArrayList<>();

    public void addAddress(UserAddress address) {
        addresses.add(address);
        address.setUser(this);
    }

    public void removeAddress(UserAddress address) {
        addresses.remove(address);
        address.setUser(null);
    }

}
