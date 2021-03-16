package com.mike.datatranslator.service.iml;

import com.mike.datatranslator.FileUtil;
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

    private long batchSizeCounter=0;

    private long processedBatchCounter=0;

    @Value("${app.config.vendorData.path}")
    private String vendorDataPath;

    @Value("${app.config.processedData.path}")
    private String processedDataPath;

    @Autowired
    ApplicationConfig.AppConfigData appConfigData;

    @Autowired
    FileUtil fileUtil;

    @Override
    public void translate() {
        FileInputStream vendorDataInputStream = null;
        Scanner vendorDataScanner = null;
        batchSizeCounter=0;
        processedBatchCounter=0;
        try {
            vendorDataInputStream = new FileInputStream(vendorDataPath);
            vendorDataScanner = new Scanner(vendorDataInputStream, "UTF-8");
            String headerLine = vendorDataScanner.useDelimiter("\n").nextLine();
            LOGGER.info("Starting Data Processing");
            fileUtil.createWriter(processedDataPath);
            processHeader(headerLine);
            findIndexsOfColumns(headerLine);
            vendorDataScanner.
                    useDelimiter("\n")
                    .forEachRemaining(this::processDataLine);
            fileUtil.closeWriter();
            LOGGER.info("Completed processing batch of {} lines with input size of {} ", processedBatchCounter, batchSizeCounter);
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

    private void processHeader(String headerLine){
       Arrays
                .asList(headerLine.split("    "))
                .stream()
                .filter(h -> appConfigData.getColumnConfigMap().containsKey(h))
                .forEach(this::formHeader);
        fileUtil.writeLineToFile(newHeader.toString());
        LOGGER.debug(newHeader.toString());
        newHeader.delete(0, newHeader.length());

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
    public void processDataLine(String dataLine){
        batchSizeCounter++;
        processedDataLine.delete(0, processedDataLine.length());
        dataIndexCounter = 0;
        Optional.of(dataLine).filter(this::isNeededVendorData).ifPresent( dl -> Arrays
                    .asList(dataLine
                            .split("    "))
                    .forEach(this::processData)
        );

        Optional
                .of(processedDataLine)
                .filter(pdl -> pdl.length() > 0)
                .ifPresent(pdl -> {
                    processedBatchCounter++;
                    fileUtil.writeLineToFile(pdl.toString());
                });
        //LOGGER.info(processedDataLine.toString());
    }

    private boolean isNeededVendorData(final String dataLine){
        return appConfigData
                .getVendorConfigMap()
                .keySet()
                .contains(Arrays
                        .asList(dataLine.split("    "))
                        .get(0));
    }

    @Async("asyncExecutor")
    private void processData(final String data){

        String derivedData = data;
        Optional
                .of(columnIndexList)
                .filter(cil -> cil.contains(dataIndexCounter))
                .ifPresent(cil -> processedDataLine
                        .append(dataIndexCounter == 0 ? appConfigData.getVendorConfigMap().get(data) : data)
                        .append("    ")
                );
        dataIndexCounter++;
    }
}
