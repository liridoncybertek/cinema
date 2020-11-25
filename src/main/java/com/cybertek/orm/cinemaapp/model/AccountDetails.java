package com.cybertek.orm.cinemaapp.model;

import com.cybertek.orm.cinemaapp.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "account_details")
@Getter
@Setter
public class AccountDetails extends Model<Integer> {

    private String name;

    private String address;

    private String country;

    private String state;

    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @OneToOne(mappedBy = "accountDetails")
    @JsonIgnore
    private User user;

}
