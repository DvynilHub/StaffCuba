package com.company.persacc.service;

import com.company.persacc.entity.Staff;
import com.haulmont.cuba.core.app.EmailService;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service(CreateAccountService.NAME)
public class CreateAccountServiceBean implements CreateAccountService {


    @Inject
    private EmailService emailService;
    @Inject
    private PasswordEncryption passwordEncryption;
    @Inject
    private DataManager dataManager;
    @Inject
    private DataManager dataManagerForGroup;
    @Inject
    private DataManager dataManagerForUserRole;
    @Inject
    private DataManager dataManagerForRole;

    /**
     * Метод для создания профиля
     * @param staff
     */
    @Override
    public void createAccount(Staff staff) {

        //Получение сотрудника из бд по заданному логину(почта)
        Optional<User> userq = dataManager.load(User.class)
                .query("select e from sec$User e where e.login = :login")
                .parameter("login", staff.getEmail())
                .optional();

        //Если запрос вернул пустого сотрудника - аккаунта нет -> создать аккаунт
        if (!userq.isPresent()) {
            User user = dataManager.create(User.class);
            user.setLogin(staff.getEmail());
            user.setPassword(passwordEncryption.getPasswordHash(user.getId(), "123"));
            user.setGroup(dataManagerForGroup.getReference(Group.class, UUID.fromString("0fa2b1a5-1d68-4d69-9fbd-dff348347f93")));
            user.setChangePasswordAtNextLogon(true);
            user.setActive(true);
            dataManager.commit(user);

            /*
            Была попытка добавить роль для сотрудника.
            1)Получить экземпляр UserRole для сотрудника по юзеру
            2)Получить кастомную роль с правами для изменения пароля
            3)установить роль в userRole
            Возникла проблема с userRole.setRole(role);
            Решил проблему установив кастомную роль дефолтной для новых пользователей.
             */
//            UserRole userRole = dataManagerForUserRole.load(UserRole.class)
//                    .query("select e from  sec$UserRole e where e.user = :user")
//                    .parameter("user",user)
//                    .one();
//            Role role = dataManagerForRole.load(Role.class)
//                    .query("select e from sec$Role e where e.locName =:rolename ")
//                    .parameter("rolename", "sos")
//                    .one();
//            userRole.setRole(role);
//            dataManagerForUserRole.commit(userRole);
        }

        //Отправка письма по шаблону
        EmailInfo emailInfo = EmailInfoBuilder.create()
                .setAddresses(staff.getEmail())
                .setCaption("PASSWORD")
                .setTemplatePath("com/company/persacc/core/templates/news_item.txt")
                .setTemplateParameters(Collections.singletonMap("staff",staff))
                .build();
        emailService.sendEmailAsync(emailInfo);

    }
}