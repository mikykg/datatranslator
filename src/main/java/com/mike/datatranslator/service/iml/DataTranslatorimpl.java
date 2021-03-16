package com.mike.datatranslator.service.iml;

import com.mike.datatranslator.config.ApplicationConfig;
import com.mike.datatranslator.service.DataTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@Service
public class DataTranslatorimpl implements DataTranslator {

    private StringBuffer newHeader = new StringBuffer();

    private StringBuffer processedDataLine = new StringBuffer();


    List<Integer> columnIndexList = new ArrayList<>();


    private int dataIndexCounter = 0;

    @Autowired
    ApplicationConfig.AppConfigData appConfigData;

    @Override
    public void translate() {
        System.out.println("I am a translator !");
        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream("/Users/michaelgeorge/my works/datatranslator/resources/testfiles/data.dat");
            sc = new Scanner(inputStream, "UTF-8");
            String headerLine = sc.useDelimiter("\n").nextLine();
            System.out.println("--------------NEW FILE-------");
            processHeader(headerLine);
            findIndexsOfColumns(headerLine);
            sc.useDelimiter("\n").forEachRemaining(this::processDataLine);
           /* while (sc.hasNextLine()) {
                //Thread.sleep(1000);
                String line = sc.nextLine();
                System.out.println(line);
            }*/
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sc != null) {
                sc.close();
            }
        }
    }

    private void processDataLine(String dataLine){
        processedDataLine.delete(0, processedDataLine.length());
        dataIndexCounter = 0;
        Arrays.asList(dataLine.split("    ")).forEach(this::processData);
        System.out.println(processedDataLine);
    }


    private void processHeader(String headerLine){

       Arrays
                .asList(headerLine.split("    "))
                .stream()
                .filter(h -> appConfigData.getColumnConfigMap().containsKey(h))
                .forEach(this::formHeader);
        System.out.println(newHeader);
    }

    private void formHeader(String data){
         newHeader.append(appConfigData.getColumnConfigMap().get(data)).append("    ");
    }

    private void findIndexsOfColumns(String headerLine){
         appConfigData
                .getColumnConfigMap()
                .keySet()
                .forEach(key -> columnIndexList.add(Arrays.asList(headerLine.split("    ")).indexOf(key)));
    }

    private void processData(String data){
        Optional.of(columnIndexList).filter(cil -> cil.contains(dataIndexCounter)).ifPresent(cil -> processedDataLine.append(data).append("    "));
        dataIndexCounter++;
    }
}
