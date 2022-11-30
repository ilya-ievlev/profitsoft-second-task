package com.profitsoft.second_task.data_processors.impl;

import com.profitsoft.second_task.data_processors.interfaces.DataProcessor;
import com.profitsoft.second_task.models.Person;
import com.profitsoft.second_task.models.ViolationType;
import com.profitsoft.second_task.parsers.impl.StaxParserXmlToJavaImpl;
import com.profitsoft.second_task.parsers.interfaces.StaxParser;

import javax.xml.stream.XMLStreamException;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class DataProcessorImpl implements DataProcessor {
    private final StaxParser staxParser = new StaxParserXmlToJavaImpl();
    private final Map<Integer, Double> violationsOfSpeeding = new HashMap<>();
    private final Map<Integer, Double> violationsOfDrunkDriving = new HashMap<>();
    private final Map<Integer, Double> violationsOfRedLight = new HashMap<>();
    private Map<Integer, Double> sortedViolationsOfSpeeding = new LinkedHashMap<>();
    private Map<Integer, Double> sortedViolationsOfDrunkDriving = new LinkedHashMap<>();
    private Map<Integer, Double> sortedViolationsOfRedLight = new LinkedHashMap<>();

    @Override
    public void processData() throws XMLStreamException {
        staxParser.parseData();
        List<Person> unsortedPersonList = staxParser.getPersonList();
        for (Person person : unsortedPersonList) {
            if (person.getViolationType().equals(ViolationType.DRUNK_DRIVING)) {
                addFineToExistingAmountInMapOrAddNewYear(violationsOfDrunkDriving, person);
            } else if (person.getViolationType().equals(ViolationType.SPEEDING)) {
                addFineToExistingAmountInMapOrAddNewYear(violationsOfSpeeding, person);
            } else if (person.getViolationType().equals(ViolationType.RED_LIGHT)) {
                addFineToExistingAmountInMapOrAddNewYear(violationsOfRedLight, person);
            }
        }
        sortedViolationsOfSpeeding = sortMap(violationsOfSpeeding);
        sortedViolationsOfDrunkDriving = sortMap(violationsOfDrunkDriving);
        sortedViolationsOfRedLight = sortMap(violationsOfRedLight);
    }

    @Override
    public Map<Integer, Double> getSortedViolationsOfSpeeding() {
        return sortedViolationsOfSpeeding;
    }

    @Override
    public Map<Integer, Double> getSortedViolationsOfDrunkDriving() {
        return sortedViolationsOfDrunkDriving;
    }

    @Override
    public Map<Integer, Double> getSortedViolationsOfRedLight() {
        return sortedViolationsOfRedLight;
    }

    private Map<Integer, Double> sortMap(Map<Integer, Double> mapToSort) {
        return mapToSort
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    private void addFineToExistingAmountInMapOrAddNewYear(Map<Integer, Double> map, Person person) {
        int yearOfViolation = person.getDateTime().getYear();
        if (map.containsKey(yearOfViolation)) {
            Double existingFine = map.get(yearOfViolation);
            Double personFineAmount = person.getFineAmount();
            map.put(yearOfViolation, existingFine + personFineAmount);
        } else {
            map.put(yearOfViolation, person.getFineAmount());
        }
    }
}
