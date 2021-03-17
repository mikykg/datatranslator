package com.mike.datatranslator.scheduler;

import com.mike.datatranslator.service.DataTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTranslator {

    @Autowired
    DataTranslator dataTranslator;

    @Value("${app.config.processedData.path}")
    private String processedDataPath;

    @Scheduled(fixedDelayString = "${app.config.schedule}")
    public void triggerRelaunchEvents() {
        dataTranslator.translate();
    }
}
