package com.livedocs.server.webapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    @Column(name = "dob", nullable = false)
    private String dob;
    @Column(name = "country")
    private String country;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getDOB() {
        return this.dob;
    }

    public String getCountry() {
        return this.country;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public static Users create() {
        return new Users();
    }

    public Users setName(String name) {
        this.name = name;
        return this;
    }

    public Users setAge(int age) {
        this.age = age;
        return this;
    }

    public Users setDOB(String dob) {
        this.dob = dob;
        return this;
    }

    public Users setCountry(String country) {
        this.country = country;
        return this;
    }

    public Users setEmail(String email) {
        this.email = email;
        return this;
    }

    public Users setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Users setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "Customer{ id:" + this.getId() + ", name:" + this.getName() + ",age:" + this.getAge() + ",city:"
                + this.getDOB() + ",country:" + this.getCountry() + ",email" + this.getEmail() + "}";
    }

    public boolean equals(Users c1) {
        return this.getId() == c1.getId() && this.getName().equals(this.getName()) && this.getAge() == c1.getAge()
                && this.getDOB().equals(c1.getDOB()) && this.getCountry().equals(c1.getCountry())
                && this.getEmail().equals(c1.getEmail());
    }

}
