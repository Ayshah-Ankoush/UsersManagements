package com.codeWithAyshah.usersmanagment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "useraddresses")
public class UserAddresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "building_number")
    private String buildingNumber;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserAddresses() {
    }

    public UserAddresses(
            String city,
            String street,
            String buildingNumber
    ) {
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;

    }


}
