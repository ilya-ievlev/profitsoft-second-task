package com.profitsoft.second_task.parsers.impl;

import com.profitsoft.second_task.models.Person;
import com.profitsoft.second_task.models.ViolationType;
import com.profitsoft.second_task.parsers.interfaces.StaxParser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StaxParserXmlToJavaImpl implements StaxParser {

    private static final String PERSON = "person";
    private static final String FINES = "fines";
    private static final String DATE_TIME = "date_time";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String TYPE = "type";
    private static final String FINE_AMOUNT = "fine_amount";
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final File inputFileFolder = new File("src/main/resources/second_task/input_xml");
    private final File[] fileList = inputFileFolder.listFiles();
    private List<Person> personList;

    @Override
    public void parseData() throws XMLStreamException {
        Person person = null;
        String content = null;
        XMLInputFactory factory = XMLInputFactory.newInstance();
        for (File inputFile : fileList) {
            String filePathFromProjectRoot = inputFile.getPath();
            String relativeFilePath = filePathFromProjectRoot.replace("src/main/resources/", ""); //it is needed to be able to pass correct path to file
            XMLStreamReader reader =
                    factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(relativeFilePath));

            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (PERSON.equals(reader.getLocalName())) {
                            person = new Person();
                        }
                        if (FINES.equals(reader.getLocalName())) {
                            if (personList == null) {
                                personList = new ArrayList<>();
                            }
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        content = reader.getText().trim();
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        switch (reader.getLocalName()) {
                            case PERSON:
                                personList.add(person);
                                break;
                            case DATE_TIME:
                                person.setDateTime(LocalDateTime.parse(content, dateTimeFormatter));
                                break;
                            case FIRST_NAME:
                                person.setFirstName(content);
                                break;
                            case LAST_NAME:
                                person.setLastName(content);
                                break;
                            case TYPE:
                                person.setViolationType(ViolationType.valueOf(content));
                                break;
                            case FINE_AMOUNT:
                                person.setFineAmount(Double.parseDouble(content));
                                break;
                        }
                        break;
                }
            }
        }
    }

    @Override
    public List<Person> getPersonList() {
        return personList;
    }
}
