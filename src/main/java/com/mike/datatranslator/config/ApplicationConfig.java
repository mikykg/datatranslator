package com.mike.datatranslator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Configuration
public class ApplicationConfig {

    private Map columnConfigMap = new HashMap<String, String>();
    private Map vendorConfigMap = new HashMap<String, String>();

    public class AppConfigData {
        private Map columnConfigMap = new HashMap<String, String>();
        private Map vendorConfigMap = new HashMap<String, String>();

        public Map getColumnConfigMap() {
            return columnConfigMap;
        }

        public void setColumnConfigMap(Map columnConfigMap) {
            this.columnConfigMap = columnConfigMap;
        }

        public Map getVendorConfigMap() {
            return vendorConfigMap;
        }

        public void setVendorConfigMap(Map vendorConfigMap) {
            this.vendorConfigMap = vendorConfigMap;
        }
    }

    AppConfigData appConfigData = new AppConfigData();

    @PostConstruct
    private void loadConfig(){
        {
            System.out.println("I am a config loader !");
            FileInputStream columnConfigInputStream = null;
            FileInputStream vendorConfigInputStream = null;
            Scanner columnConfigscanner = null;
            Scanner vendorConfigscanner = null;

            try {
                columnConfigInputStream = new FileInputStream("/Users/michaelgeorge/my works/datatranslator/resources/testfiles/column.config");
                vendorConfigInputStream = new FileInputStream("/Users/michaelgeorge/my works/datatranslator/resources/testfiles/vendor.config");

                columnConfigscanner = new Scanner(columnConfigInputStream, "UTF-8");
                columnConfigscanner.useDelimiter("\n").forEachRemaining(this::mapColumn);

                vendorConfigscanner = new Scanner(vendorConfigInputStream, "UTF-8");
                vendorConfigscanner.useDelimiter("\n").forEachRemaining(this::mapVendor);
                appConfigData.setColumnConfigMap(this.columnConfigMap);
                appConfigData.setVendorConfigMap(this.vendorConfigMap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }  finally {
                if (columnConfigInputStream != null) {
                    try {
                        columnConfigInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (columnConfigscanner != null) {
                    columnConfigscanner.close();
                }

                if (vendorConfigInputStream != null) {
                    try {
                        vendorConfigInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (vendorConfigscanner != null) {
                    columnConfigscanner.close();
                }
            }
        }
    }
    private void mapColumn(final String columnLine)  {
        columnConfigMap
                .put(columnLine.split("    ")[0],columnLine
                        .split("    ")[1]);
    }

    private void mapVendor(final String vendorLine)  {
       vendorConfigMap
               .put(vendorLine.split("    ")[0],vendorLine
                       .split("    ")[1]);
    }

    @Bean
    public AppConfigData getAppConfigData(){
        return appConfigData;
    }
}
