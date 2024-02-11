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
    @Column(name = "salary")
    private int salary;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name="password", nullable = false)
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

    public int getSalary() {
        return this.salary;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword(){
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

    public Users setSalary(int salary) {
        this.salary = salary;
        return this;
    }

    public Users setCity(String city) {
        this.city = city;
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

    public Users setPassword(String password){
        this.password=password;
        return this;
    }

    @Override
    public String toString() {
        return "Customer{ id:" + this.getId() + ", name:" + this.getName() + ",age:" + this.getAge() + ",city:"
                + this.getCity() + ",country:" + this.getCountry() + ",email" + this.getEmail() + "}";
    }

    public boolean equals(Users c1) {
        return this.getId() == c1.getId() && this.getName().equals(this.getName()) && this.getAge() == c1.getAge()
                && this.getCity().equals(c1.getCity()) && this.getCountry().equals(c1.getCountry())
                && this.getEmail().equals(c1.getEmail());
    }

}
