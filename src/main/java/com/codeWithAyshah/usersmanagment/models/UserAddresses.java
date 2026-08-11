package com.codeWithAyshah.usersmanagment.models;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "useraddresses")
public class UserAddresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "address")
    private String address;
    @Column(name = "user_id", insertable = false, updatable = false)
    private int userId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserAddresses() {

    }

    public UserAddresses(int id, String address, int userId) {
        this.id = id;
        this.address = address;
        this.userId = userId;
    }


}
