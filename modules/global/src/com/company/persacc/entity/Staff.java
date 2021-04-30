package com.company.persacc.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Table(name = "PERSACC_STAFF")
@Entity(name = "persacc_Staff")
@NamePattern("%s|lastName")
public class Staff extends StandardEntity {
    private static final long serialVersionUID = -5580826276002317173L;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "PATRONYMIC")
    private String patronymic;

    @Column(name = "EMAIL", nullable = false, unique = true)
    @NotNull
    @Email
    private String email;

    @Column(name = "POST")
    private String post;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "PHONE")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}