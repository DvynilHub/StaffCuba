package com.company.persacc.service;

import com.company.persacc.entity.Staff;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service(CSVParsingService.NAME)
public class CSVParsingServiceBean implements CSVParsingService {

    @Inject
    protected DataManager dataManager;

    /**
     * Метод для проверки части колонки
     * @param text
     * @return
     */
    @Override
    public boolean IsColumnPart(String text) {
        String trimText = text.trim();
        //Если в тексте одна ковычка и текст на нее заканчиваеться значит это часть предыдущей колонки
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }

    /**
     * Метод для создания листа сотрудников из csv-файла таблицы
     * @param filePath
     * @return List<Staff> staffs - лист сотрудников
     * @throws IOException
     */
    @Override
    public List<Staff> ParseStaffCsv(String filePath) throws IOException {
        //Загружаем строки из файла
        List<Staff> staffs = new ArrayList<>();
        List<String> fileLines = Files.readAllLines(Paths.get(filePath));
        for (String fileLine : fileLines) {
            String[] splitedText = fileLine.split(",");
            ArrayList<String> columnList = new ArrayList<>();
            for (int i = 0; i < splitedText.length; i++) {
                //Если колонка начинается на кавычки или заканчиваеться на кавычки
                if (IsColumnPart(splitedText[i])) {
                    String lastText = columnList.get(columnList.size() - 1);
                    columnList.set(columnList.size() - 1, lastText + ","+ splitedText[i]);
                } else {
                    columnList.add(splitedText[i]);
                }
            }

            Staff staff = dataManager.create(Staff.class);
            staff.setLastName(columnList.get(0));
            staff.setFirstName(columnList.get(1));
            staff.setPatronymic(columnList.get(2));
            //staff.setPhone(columnList.get(3));
            staff.setEmail(columnList.get(4));
            staff.setPost(columnList.get(5));
            staff.setCompany(columnList.get(6));
            staffs.add(staff);
        }
        return staffs;
    }
}