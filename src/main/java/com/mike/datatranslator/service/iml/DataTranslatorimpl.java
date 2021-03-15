package com.mike.datatranslator.service.iml;

import com.mike.datatranslator.service.DataTranslator;
import org.springframework.stereotype.Service;

@Service
public class DataTranslatorimpl implements DataTranslator {

    @Override
    public void translate() {
        System.out.println("I am a translator !");
    }
}
