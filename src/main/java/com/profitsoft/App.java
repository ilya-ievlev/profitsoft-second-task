package com.profitsoft;

import com.profitsoft.first_task.impl.SpecialXmlParserImpl;

public class App {
    private static final SpecialXmlParserImpl specialXmlParser = new SpecialXmlParserImpl();

    public static void main(String[] args) {
//        startFirstTask();
        startSecondTask();
    }

    private static void startFirstTask() {
        specialXmlParser.getPersonInfo();
        specialXmlParser.changeStringBuilder();
        specialXmlParser.writeStringToFile();
    }

    private static void startSecondTask() {

    }
}
