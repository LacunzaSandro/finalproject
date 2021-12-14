package com.informatorio.finalproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.informatorio.finalproject.entity.UserEnum;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserUpdateDto {
    private Long id;
    @NotBlank(message = "name must not be empty")
    private String FirstName;
    @NotBlank(message = "lastname must not be empty")
    private String lastName;
    @Column(length = 150, nullable = false, unique = true)
    @NotBlank(message = "email must not be empty")
    @Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "email should be a valid email")
    private String email;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private UserEnum type;
    @NotBlank(message = "city must not be empty")
    private String city;
    @NotBlank(message = "province must not be empty")
    private String province;
    @NotBlank(message = "country must not be empty")
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserEnum getType() {
        return type;
    }

    public void setType(UserEnum type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
