package com.mike.datatranslator.service.iml;

import com.mike.datatranslator.config.ApplicationConfig;
import com.mike.datatranslator.service.DataTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
@Configuration
public class DataTranslatorimpl implements DataTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataTranslatorimpl.class);

    private StringBuffer newHeader = new StringBuffer();

    private StringBuffer processedDataLine = new StringBuffer();

    List<Integer> columnIndexList = new ArrayList<>();

    private int dataIndexCounter = 0;

    @Value("${app.config.vendorData.path}")
    private String vendorDataPath;

    @Value("${app.config.processedData.path}")
    private String processedDataPath;

    @Autowired
    ApplicationConfig.AppConfigData appConfigData;

    @Override
    public void translate() {
        FileInputStream vendorDataInputStream = null;
        Scanner vendorDataScanner = null;
        try {
            vendorDataInputStream = new FileInputStream(vendorDataPath);
            vendorDataScanner = new Scanner(vendorDataInputStream, "UTF-8");
            String headerLine = vendorDataScanner.useDelimiter("\n").nextLine();
            LOGGER.info("--------------NEW FILE-------");
            processHeader(headerLine);
            findIndexsOfColumns(headerLine);
            vendorDataScanner.
                    useDelimiter("\n")
                    .forEachRemaining(this::processDataLine);
            if (vendorDataScanner.ioException() != null) {
                throw vendorDataScanner.ioException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (vendorDataInputStream != null) {
                try {
                    vendorDataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (vendorDataScanner != null) {
                vendorDataScanner.close();
            }
        }
    }

    @Async("asyncExecutor")
    public void processDataLine(String dataLine){
        processedDataLine.delete(0, processedDataLine.length());
        dataIndexCounter = 0;
        Arrays.
                asList(dataLine
                        .split("    "))
                .forEach(this::processData);
        LOGGER.info(processedDataLine.toString());
    }



    private void processHeader(String headerLine){
       Arrays
                .asList(headerLine.split("    "))
                .stream()
                .filter(h -> appConfigData.getColumnConfigMap().containsKey(h))
                .forEach(this::formHeader);
        //System.out.println(newHeader);
        LOGGER.info(newHeader.toString());
    }

    private void formHeader(String data){
         newHeader
                 .append(appConfigData.getColumnConfigMap().get(data))
                 .append("    ");
    }

    private void findIndexsOfColumns(String headerLine){
         appConfigData
                .getColumnConfigMap()
                .keySet()
                .forEach(key -> columnIndexList
                        .add(Arrays.asList(headerLine
                                .split("    "))
                                .indexOf(key)));
    }

    @Async("asyncExecutor")
    private void processData(String data){
        Optional
                .of(columnIndexList)
                .filter(cil -> cil.contains(dataIndexCounter))
                .ifPresent(cil -> processedDataLine
                        .append(data)
                        .append("    "));
        dataIndexCounter++;
    }
}
