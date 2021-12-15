package com.informatorio.finalproject.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    @NotBlank(message = "name must not be empty")
    private String FirstName;
    @Column(length = 50, nullable = false)
    @NotBlank(message = "lastname must not be empty")
    private String lastName;
    @Column(length = 150, nullable = false,  unique = true)
    @NotBlank(message = "email must not be empty")

    @Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "email should be a valid email")
    private String email;
    @Column(length = 20, nullable = false)
    @NotBlank(message = "password must not be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(max = 20, min = 8, message = "password must be between 8 and 20 characters")
    private String password;
    @CreationTimestamp
    private LocalDateTime create_at;
    @UpdateTimestamp
    private LocalDateTime update_at;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private UserEnum type;
    @NotBlank(message = "city must not be empty")
    private String city;
    @NotBlank(message = "province must not be empty")
    private String province;
    @NotBlank(message = "country must not be empty")
    private String country;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner", cascade = {CascadeType.ALL})
    public List<Emprendimiento> emprendimientos = new ArrayList<>();
    @OneToMany(targetEntity = Vote.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")

    private List<Vote> votes;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
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

    public List<Emprendimiento> getEmprendimientos() {
        return emprendimientos;
    }

    public void setEmprendimientos(List<Emprendimiento> emprendimientos) {
        this.emprendimientos = emprendimientos;
    }
    public void addEmprendimiento(Emprendimiento emprendimiento){
        this.emprendimientos.add(emprendimiento);
        emprendimiento.setOwner(this);
    }
}
