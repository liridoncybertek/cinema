package com.cybertek.orm.cinemaapp.model;

import com.cybertek.orm.cinemaapp.model.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_account")
@Getter
@Setter
public class User extends Model<Integer> {

    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(min = 6, message = "Password should not be less than 6")
    private String password;

    @NotNull
    @Column(unique = true)
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_details_id", referencedColumnName = "id")
    private AccountDetails accountDetails;
}
