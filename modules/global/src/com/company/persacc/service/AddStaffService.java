package com.company.persacc.service;

import com.company.persacc.entity.Staff;

import java.util.List;

public interface AddStaffService {
    String NAME = "persacc_AddStaffService";

    List<Staff> loadAllStaff();

    Staff createStaff(String lastName, String firstName, String email, String patronymic, String post, String company);

    void updateStaff(Staff staffSheet, Staff staffDB);
}