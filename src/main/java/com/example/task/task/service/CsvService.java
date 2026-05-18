package com.example.task.task.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.task.task.entity.Tenant;

@Service
public class CsvService {

    public void parseCsv(MultipartFile file, Consumer<Tenant> consumer) throws Exception {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        file.getInputStream()));

        reader.readLine();

        String line;

        while ((line = reader.readLine()) != null) {

            String[] data = line.split(",");

            Tenant tenant = Tenant.builder()
                    .name(data[0])
                    .email(data[1])
                    .phone(data[2])
                    .build();

            consumer.accept(tenant);
        }
    }


}
