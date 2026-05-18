package com.example.task.task.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.task.task.entity.NotificationStatus;
import com.example.task.task.entity.Recipient;
import com.opencsv.CSVReader;
@Service
public class CsvService {

    public List<Recipient> parseCsv(MultipartFile file)
            throws Exception {

        List<Recipient> recipients = new ArrayList<>();

        try (
            Reader reader =
                new InputStreamReader(file.getInputStream())
        ) {

            CSVReader csvReader =new CSVReader(reader);

            List<String[]> rows =csvReader.readAll();

            rows.remove(0);

            for (String[] row : rows) {

                Recipient recipient =
                        Recipient.builder()
                                .name(row[0])
                                .email(row[1])
                                .phone(row[2])
                                .status(NotificationStatus.PENDING)
                                .build();

                recipients.add(recipient);
            }
        }

        return recipients;
    }
}
