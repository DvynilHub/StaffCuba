package com.company.persacc.service;

import com.company.persacc.entity.Staff;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service(AddStaffService.NAME)
public class AddStaffServiceBean implements AddStaffService {
    @Inject
    private DataManager dataManager;

    /**
     * Метод для получение всех сартрудников
     * @return List<Staff> - лист сатрудников
     */
    @Override
    public List<Staff> loadAllStaff() {
        return dataManager.load(Staff.class)
                .query("select e from persacc_Staff e")
                .list();

    }

    /**
     * Метод для добавление сотрудника в бд
     * @param lastName
     * @param firstName
     * @param email
     * @param patronymic
     * @param post
     * @param company
     * @return новый сотрудник
     */
    @Override
    public Staff createStaff(String lastName, String firstName, String email, String patronymic, String post, String company) {
        Staff staff = dataManager.create(Staff.class);
        staff.setLastName(lastName);
        staff.setFirstName(firstName);
        staff.setEmail(email);
        staff.setPatronymic(patronymic);
        staff.setPost(post);
        staff.setCompany(company);

        return dataManager.commit(staff);
    }

    /**
     * Метод для обновления сатрудников, если есть отличия с таблицей
     * @param staffSheet - сотрудник из таблицы
     * @param staffDB - сотрудник в бд
     */
    @Override
    public void updateStaff(Staff staffSheet, Staff staffDB) {
        int i=0;
        if (!staffSheet.getLastName().equals(staffDB.getLastName())){
            staffDB.setLastName(staffSheet.getLastName());
            i++;
        }
        if (!staffSheet.getFirstName().equals(staffDB.getFirstName())){
            staffDB.setFirstName(staffSheet.getFirstName());
            i++;
        }
        if (!staffSheet.getEmail().equals(staffDB.getEmail())){
            staffDB.setEmail(staffSheet.getEmail());
            i++;
        }
        if (!staffSheet.getPatronymic().equals(staffDB.getPatronymic())){
            staffDB.setPatronymic(staffSheet.getPatronymic());
            i++;
        }
        if (!staffSheet.getPost().equals(staffDB.getPost())){
            staffDB.setPost(staffSheet.getPost());
            i++;
        }
        if (!staffSheet.getCompany().equals(staffDB.getCompany())){
            staffDB.setCompany(staffSheet.getCompany());
            i++;
        }
        if (i>0){
            dataManager.commit(staffDB);
        }

    }


}