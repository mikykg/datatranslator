package com.mike.datatranslator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
@Configuration
public class FileUtil {

    @Value("${app.config.columnConfig.path}")
    private String columnConfigPath;

    @Value("${app.config.vendorConfig.path}")
    private String vendorConfigPath;

    @Value("${app.config.vendorData.path}")
    private String vendorDataPath;

    @Value("${app.config.processedData.path}")
    private String processedDataPath;

    BufferedWriter writer = null;

    public void createWriter(String filePath) throws IOException {
         writer = new BufferedWriter(new FileWriter(processedDataPath, true));
    }

    public void writeLineToFile(String line) {
        try {
            writer.write(line);
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWriter() throws IOException {
        writer.close();

    }
}
