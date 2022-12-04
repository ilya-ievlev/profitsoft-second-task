package com.profitsoft.second_task.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitsoft.second_task.data_processors.DataProcessor;
import com.profitsoft.second_task.dto.SortedOutputDataTransferObject;

import java.io.File;
import java.io.IOException;

public class JacksonJavaToJsonParser {
    private static final File resultFile = new File("src/main/resources/second_task/output_json/output.json");

    public static void parseData(File inputFileFolder) throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        SortedOutputDataTransferObject transferObject = DataProcessor.processData(inputFileFolder);
        try {
            jsonMapper.writeValue(resultFile, transferObject);
        } catch (IOException e) {
            throw new IOException("error in jsonMapper", e);
        }
    }
}
