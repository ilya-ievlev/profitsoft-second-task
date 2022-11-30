package com.profitsoft.second_task.data_processors.interfaces;

import javax.xml.stream.XMLStreamException;
import java.util.Map;

public interface DataProcessor {
    void processData() throws XMLStreamException;

    Map<Integer, Double> getSortedViolationsOfSpeeding();

    Map<Integer, Double> getSortedViolationsOfDrunkDriving();

    Map<Integer, Double> getSortedViolationsOfRedLight();
}
