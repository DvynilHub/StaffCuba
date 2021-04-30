package com.company.persacc.web.screens.staff;

import com.company.persacc.entity.Staff;
import com.company.persacc.service.AddStaffService;
import com.company.persacc.service.CSVParsingService;
import com.company.persacc.service.CreateAccountService;
import com.company.persacc.service.DownloadCsvService;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;

import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@UiController("persacc_Staff.browse")
@UiDescriptor("staff-browse.xml")
@LookupComponent("staffsTable")
@LoadDataBeforeShow
public class StaffBrowse extends StandardLookup<Staff> {
    @Inject
    private CSVParsingService cSVParsingService;
    @Inject
    private Notifications notifications;
    @Inject
    private DownloadCsvService downloadCsvService;

    @Inject
    private AddStaffService addStaffService;
    @Inject
    private CreateAccountService createAccountService;



    @Subscribe("clic")
    public void onClicClick(Button.ClickEvent event) throws IOException {

        //ссылка на таблицу и путь к файлу таблицы
        String sheetURL="https://docs.google.com/spreadsheets/d/1PiJi6zPyYw5nAx3bJi56haKLlgHpYQEfUnF6Kujjbs4/export?format=csv";
        String csvFilePath = "D:\\persacc\\modules\\global\\src\\com\\company\\persacc\\resources\\Sheet.csv"; //хардкод, не видит через com...

        downloadCsvService.download(sheetURL, csvFilePath); //скачивание таблицы
        List<Staff> allStaff = addStaffService.loadAllStaff(); //Сотрудники из бд
        //цикл по сотрудникам из таблицы
        for (Staff row: cSVParsingService.ParseStaffCsv(csvFilePath)){
            int j =0;
          for (int i=0;i<allStaff.size();i++){

              if (row.getEmail().equals(allStaff.get(i).getEmail())){ //если email совпал, то обновить
                  j++;
                  addStaffService.updateStaff(row,allStaff.get(i));
              }
          }
          if (j==0) { //иначе добавить в бд и сделать акк
              addStaffService.createStaff(
                       row.getLastName()
                      ,row.getFirstName()
                      ,row.getEmail()
                      ,row.getPatronymic()
                      ,row.getPost()
                      ,row.getCompany());

              notifications.create(Notifications.NotificationType.TRAY).withCaption("Добавлен "+row.getEmail()).show();
              createAccountService.createAccount(row);
              notifications.create(Notifications.NotificationType.TRAY).withCaption("Создан акк "+row.getEmail()).show();
            }

        }


    }
}