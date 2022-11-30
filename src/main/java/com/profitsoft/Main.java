package com.profitsoft;

import com.profitsoft.first_task.impl.SpecialXmlParserImpl;
import com.profitsoft.first_task.interfaces.SpecialXmlParser;
import com.profitsoft.second_task.parsers.impl.JacksonJavaToJsonParserImpl;
import com.profitsoft.second_task.parsers.interfaces.JacksonParser;


public class Main {
    private static final SpecialXmlParser specialXmlParser = new SpecialXmlParserImpl();
    private static final JacksonParser jacksonParser = new JacksonJavaToJsonParserImpl();

    public static void main(String[] args) {
        startFirstTask();
        startSecondTask();
    }

    private static void startFirstTask() {
        specialXmlParser.parseData();
    }

    private static void startSecondTask() {
        jacksonParser.parseData();
    }
}
