package com.livedocs.server.webapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rollNo;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "dob", nullable = false)
    private String dob;
    @Column(name = "college_name")
    private String collegeName;
}
