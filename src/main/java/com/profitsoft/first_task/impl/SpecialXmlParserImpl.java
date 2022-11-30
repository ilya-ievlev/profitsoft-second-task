package com.profitsoft.first_task.impl;

import com.profitsoft.first_task.interfaces.SpecialXmlParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    @Override
    public void parseData() {
        try (Scanner scanner = new Scanner(inputFile, StandardCharsets.UTF_8).useDelimiter("(>)");
             FileWriter fileWriter = new FileWriter("src/main/resources/first_task_output_file.xml", Charset.defaultCharset())) {
            while (scanner.hasNext()) {
                StringBuilder scannerString = new StringBuilder(scanner.next());
                Matcher surnameMatcher = personSurnamePattern.matcher(scannerString);
                Matcher nameMatcher = personNamePattern.matcher(scannerString);
                Matcher surnameWithTagMatcher = personSurnameWithTagPattern.matcher((scannerString));
                if (nameMatcher.find() && surnameMatcher.find() && surnameWithTagMatcher.find()) {
                    String name = nameMatcher.group(1);
                    String surname = surnameMatcher.group(1);
                    String surnameWithTag = surnameWithTagMatcher.group(1);
                    int surnameWithTagLength = surnameWithTag.length();
                    int nameLength = name.length();
                    int indexOfSurnameWithTag = scannerString.indexOf(surnameWithTag);
                    scannerString.delete(indexOfSurnameWithTag, indexOfSurnameWithTag + surnameWithTagLength);

                    int indexOfName = scannerString.indexOf(name);
                    scannerString.insert(indexOfName + nameLength, " " + surname);
                }
                fileWriter.write(scannerString.toString() + ">");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
