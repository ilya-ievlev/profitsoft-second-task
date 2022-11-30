package com.profitsoft.second_task.parsers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitsoft.second_task.data_processors.impl.DataProcessorImpl;
import com.profitsoft.second_task.dto.SortedOutputDataTransferObject;
import com.profitsoft.second_task.parsers.interfaces.JacksonParser;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JacksonJavaToJsonParserImpl implements JacksonParser {
    private final DataProcessorImpl dataProcessor = new DataProcessorImpl();
    private final File resultFile = new File("src/main/resources/second_task/output_json/output.json");
    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public void parseData() {
        try {
            dataProcessor.processData();
        } catch (
                XMLStreamException e) {
            e.printStackTrace();
        }
        Map<Integer, Double> violationsOfSpeeding = dataProcessor.getSortedViolationsOfSpeeding();
        Map<Integer, Double> violationsOfDrunkDriving = dataProcessor.getSortedViolationsOfDrunkDriving();
        Map<Integer, Double> violationsOfRedLight = dataProcessor.getSortedViolationsOfRedLight();
        SortedOutputDataTransferObject sortedOutputDataTransferObject =
                new SortedOutputDataTransferObject(violationsOfSpeeding, violationsOfDrunkDriving, violationsOfRedLight);

        try {
            jsonMapper.writeValue(resultFile, sortedOutputDataTransferObject);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
