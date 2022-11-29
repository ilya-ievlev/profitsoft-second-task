package com.profitsoft.first_task.impl;

import com.profitsoft.first_task.interfaces.SpecialXmlParser;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecialXmlParserImpl implements SpecialXmlParser {
    private final File inputFile = new File("src/main/resources/first_task_input_file.xml");

    private final Pattern personNamePattern = Pattern.compile("\\s+name\\s?+=\\s?+\"(\\S+)\"\\s*\\S*");
    private final Pattern personSurnamePattern = Pattern.compile("\\s*?surname\\s?+=\\s?+\"(\\S+)\"\\s*\\S*");
    private final Pattern personSurnameWithTagPattern = Pattern.compile("\\s*(surname\\s?+=\\s?+\"\\S+\")\\s*\\S*");

    private final List<String> surnameList = new ArrayList<>();
    private final List<String> surnameListWithTag = new ArrayList<>();
    private final List<String> nameList = new ArrayList<>();
    private final StringBuilder stringFromFile = new StringBuilder();

    private final Map<String, Integer> nameDuplicateMap = new HashMap<>();

    @Override
    public void getPersonInfo() {
        try (Scanner scanner = new Scanner(inputFile, StandardCharsets.UTF_8)) {
            while (scanner.hasNext()) {
                String scannerString = scanner.nextLine();
                stringFromFile.append(scannerString).append("\n");
                Matcher surnameMatcher = personSurnamePattern.matcher(scannerString);
                Matcher nameMatcher = personNamePattern.matcher(scannerString);
                Matcher surnameWithTagMatcher = personSurnameWithTagPattern.matcher((scannerString));
                if (surnameMatcher.find()) {
                    surnameList.add(surnameMatcher.group(1));
                }
                if (nameMatcher.find()) {
                    nameList.add(nameMatcher.group(1));
                }
                if (surnameWithTagMatcher.find()) {
                    surnameListWithTag.add(surnameWithTagMatcher.group(1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeStringBuilder() {
        for (int i = 0; i < nameList.size(); i++) {
            String name = nameList.get(i);
            int nameLength = name.length();

            if (!nameDuplicateMap.containsKey(name)) {
                nameDuplicateMap.put(name, 0);
            }
            int oldIndexOfNameDuplicate = nameDuplicateMap.get(name);
            int indexOfName = stringFromFile.indexOf(name, oldIndexOfNameDuplicate + nameLength);
            nameDuplicateMap.put(name, oldIndexOfNameDuplicate + indexOfName);
            stringFromFile.insert(indexOfName + nameLength, " " + surnameList.get(i));

            String surnameWithTag = surnameListWithTag.get(i);
            int surnameWithTagLength = surnameWithTag.length();
            int indexOfSurnameWithTag = stringFromFile.indexOf(surnameWithTag);
            stringFromFile.delete(indexOfSurnameWithTag, indexOfSurnameWithTag + surnameWithTagLength);
        }
    }

    @Override
    public void writeStringToFile() {
        try (FileWriter fileWriter = new FileWriter("src/main/resources/first_task_output_file.xml", Charset.defaultCharset())) {
            fileWriter.write(stringFromFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
