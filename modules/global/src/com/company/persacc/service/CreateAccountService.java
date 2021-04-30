package com.company.persacc.service;

import com.company.persacc.entity.Staff;

public interface CreateAccountService {
    String NAME = "persacc_CreateAccountService";

    void createAccount(Staff staff);
}