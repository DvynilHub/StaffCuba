package com.company.persacc.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


@Service(DownloadCsvService.NAME)
public class DownloadCsvServiceBean implements DownloadCsvService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DownloadCsvServiceBean.class);
    private static final Logger log1 = org.slf4j.LoggerFactory.getLogger(DownloadCsvServiceBean.class);

    /**
     * Метод для скачивания csv таблицы по ссылке
     * @param url - готовая ссылка для скачивания
     * @param target - путь файла, куда сохранить таблицу
     * @throws IOException
     */
    @Override
    public void download(String url, String target) throws IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream());
            fout = new FileOutputStream(target);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } catch (MalformedURLException e) {
            log.error("Error", e);
        } catch (IOException e) {
            log1.error("Error", e);
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }
}