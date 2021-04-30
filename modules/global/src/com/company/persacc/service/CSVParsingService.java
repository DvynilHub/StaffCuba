package com.company.persacc.service;

import com.company.persacc.entity.Staff;

import java.io.IOException;
import java.util.List;

public interface CSVParsingService {
    String NAME = "persacc_CSVParsingService";

    boolean IsColumnPart(String text);

    List<Staff> ParseStaffCsv(String filePath) throws IOException;
}