package com.informatorio.finalproject.dto;

import com.informatorio.finalproject.entity.UserEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserUpdateDto {
    @NotBlank(message = "name must not be empty")
    private String FirstName;
    @NotBlank(message = "lastname must not be empty")
    private String lastName;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private UserEnum type;
    @NotBlank(message = "city must not be empty")
    private String city;
    @NotBlank(message = "province must not be empty")
    private String province;
    @NotBlank(message = "country must not be empty")
    private String country;


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
