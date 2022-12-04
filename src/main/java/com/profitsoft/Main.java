package com.profitsoft;

import com.profitsoft.first_task.SpecialXmlParser;
import com.profitsoft.second_task.parsers.JacksonJavaToJsonParser;
import java.io.File;
import java.io.IOException;


public class Main {
    private static final File inputFileFolderSecondTask = new File("src/main/resources/second_task/input_xml");
    private static final File inputFileFirstTask = new File("src/main/resources/first_task_input_file.xml");


    public static void main(String[] args) {
        startFirstTask();
        startSecondTask();
    }

    private static void startFirstTask() {
        try {
            SpecialXmlParser.parseData(inputFileFirstTask);
        } catch (IOException e) {
            e.printStackTrace(); //should be a logger
        }
    }

    private static void startSecondTask() {
        try {
            JacksonJavaToJsonParser.parseData(inputFileFolderSecondTask);
        } catch (IOException e) {
            e.printStackTrace(); //should be a logger
        }
    }
}
