package com.example.task.task.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.task.task.entity.Recipient;

@Service
public class CsvService {

    public void parseCsv(MultipartFile file, Consumer<Recipient> consumer) throws Exception {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        file.getInputStream()));

        reader.readLine();

        String line;

        while ((line = reader.readLine()) != null) {

            String[] data = line.split(",");

            Recipient recipient = Recipient.builder()
                    .name(data[0])
                    .email(data[1])
                    .phone(data[2])
                    .build();

            consumer.accept(recipient);
        }
    }


}
