package com.profitsoft.second_task.data_processors;

import com.profitsoft.second_task.dto.SortedOutputDataTransferObject;
import com.profitsoft.second_task.models.Person;
import com.profitsoft.second_task.models.ViolationType;
import com.profitsoft.second_task.parsers.StaxParserXmlToJava;

import java.io.File;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class DataProcessor {

    public static SortedOutputDataTransferObject processData(File inputFileFolder) {
        Map<Integer, Double> violationsOfSpeeding = new HashMap<>();
        Map<Integer, Double> violationsOfDrunkDriving = new HashMap<>();
        Map<Integer, Double> violationsOfRedLight = new HashMap<>();
        List<Person> unsortedPersonList = StaxParserXmlToJava.parseData(inputFileFolder);
        for (Person person : unsortedPersonList) {
            if (person.getViolationType().equals(ViolationType.DRUNK_DRIVING)) {
                addFineToExistingAmountInMapOrAddNewYear(violationsOfDrunkDriving, person);
            } else if (person.getViolationType().equals(ViolationType.SPEEDING)) {
                addFineToExistingAmountInMapOrAddNewYear(violationsOfSpeeding, person);
            } else if (person.getViolationType().equals(ViolationType.RED_LIGHT)) {
                addFineToExistingAmountInMapOrAddNewYear(violationsOfRedLight, person);
            }
        }
        return new SortedOutputDataTransferObject(sortMap(violationsOfSpeeding), sortMap(violationsOfDrunkDriving), sortMap(violationsOfRedLight));
    }

    private static LinkedHashMap<Integer, Double> sortMap(Map<Integer, Double> mapToSort) {
        return mapToSort
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    private static void addFineToExistingAmountInMapOrAddNewYear(Map<Integer, Double> map, Person person) {
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
