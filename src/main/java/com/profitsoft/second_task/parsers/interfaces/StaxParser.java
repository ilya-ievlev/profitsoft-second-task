package com.profitsoft.second_task.parsers.interfaces;

import com.profitsoft.second_task.models.Person;

import javax.xml.stream.XMLStreamException;
import java.util.List;

public interface StaxParser {
    void parseData() throws XMLStreamException;

    List<Person> getPersonList();
}
