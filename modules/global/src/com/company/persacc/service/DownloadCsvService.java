package com.company.persacc.service;

import java.io.IOException;
import java.net.MalformedURLException;

public interface DownloadCsvService {
    String NAME = "persacc_DownloadCsvService";

    void download(String url, String target) throws IOException;
}