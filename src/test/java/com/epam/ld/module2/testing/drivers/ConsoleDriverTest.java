package com.epam.ld.module2.testing.drivers;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.template.Template;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@EnabledOnOs({OS.WINDOWS, OS.LINUX, OS.MAC})
@EnabledForJreRange(min = JRE.JAVA_9)
@ExtendWith(MockitoExtension.class)
class ConsoleDriverTest {


    @DisplayName("user input for template body")
    @Tag("template_body")
    @Test
    void whenConsoleModeIsRunCheckUserInputForTemplateBody(){
        String input = "I am #{name} and I work as a #{job}.";
        Scanner scanner = mock(Scanner.class);
        when(scanner.nextLine()).thenReturn(input).thenReturn("");
        ConsoleDriver consoleDriver = new ConsoleDriver(scanner);
        Template template = consoleDriver.createTemplate();
        assertEquals(input, template.getBody());
    }
    @DisplayName("user input for key value pairs")
    @Tag("key_value")
    @Test
    void whenConsoleModeIsRunCheckUserInputForKeyValuePairs(){
        String input0 = "I am #{name} and I work as a #{job}.";
        String input1 = "John";
        String input2 = "doctor";
        Scanner scanner = mock(Scanner.class);
        when(scanner.nextLine()).thenReturn(input0).thenReturn("").thenReturn(input1).thenReturn(input2).thenReturn("");
        ConsoleDriver consoleDriver = new ConsoleDriver(scanner);
        Template template = consoleDriver.createTemplate();

        Client client = consoleDriver.createClient(template);

        Map<String, String> providedKeyValues = client.getProvidedKeyValues();

        assertAll("check values are set correctly",
                () -> assertEquals("doctor", providedKeyValues.get("job")),
                () -> assertEquals("John", providedKeyValues.get("name"))
                );
    }


    @DisplayName("user input for addresses: ")
    @Tag("addresses")
    @Test
    void whenConsoleModeIsRunCheckUserInputForAddresses(){
        String input0 = "I am #{name} and I work as a #{job}.";
        String input1 = "John";
        String input2 = "doctor";
        Scanner scanner = mock(Scanner.class);
        when(scanner.nextLine())
                .thenReturn(input0).thenReturn("") // for body
                .thenReturn(input1).thenReturn(input2) // for values
                .thenReturn("London").thenReturn("New York").thenReturn(""); // for addresses

        ConsoleDriver consoleDriver = new ConsoleDriver(scanner);
        Template template = consoleDriver.createTemplate();

        Client client = consoleDriver.createClient(template);

        Map<String, String> providedKeyValues = client.getProvidedKeyValues();
        String addresses = client.getAddresses();
        String expectedAddresses = "London,New York,";

        assertAll("check values are set correctly",
                () -> assertEquals("doctor", providedKeyValues.get("job")),
                () -> assertEquals("John", providedKeyValues.get("name")),
                () -> assertEquals(expectedAddresses, addresses)
        );
    }
}

