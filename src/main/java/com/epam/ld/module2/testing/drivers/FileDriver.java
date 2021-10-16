package com.epam.ld.module2.testing.drivers;


import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.Messenger;
import com.epam.ld.module2.testing.mailservers.FileMailServer;
import com.epam.ld.module2.testing.mailservers.MailServer;
import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FileDriver implements Driver {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        System.out.println("File driver is running");
        System.out.println("Provide absolute path of input file:");
        System.out.print("absolute_input_file_path= ");
        String inputFileAbsolutePath = scanner.nextLine();
        System.out.println("Provide absolute path of output file:");
        System.out.print("absolute_output_file_path= ");
        String outputFileAbsolutePath = scanner.nextLine();

        List<String> inputFileLines = extractLinesFromFile(inputFileAbsolutePath);

        if (inputFileLines == null) {
            throw new RuntimeException("file not found");
        }

        String body = extractBodyFromInputFileLines(inputFileLines);
        Map<String,String> keyValues = extractKeyValuesFromInputFileLines(inputFileLines);
        String addresses = extractAddressesFromInputFileLines(inputFileLines);

        Client client = new Client();
        client.setAddresses(addresses);
        client.setProvidedKeyValues(keyValues);

        Template template = new Template();
        template.setBody(body);


        MailServer mailServer = new FileMailServer(outputFileAbsolutePath);
        TemplateEngine templateEngine = new TemplateEngine();
        Messenger messenger = new Messenger(mailServer, templateEngine);

        messenger.sendMessage(client, template);
    }

    private List<String> extractLinesFromFile(String inputFileAbsolutePath) {

        try (Stream<String> stream = Files.lines(Paths.get(inputFileAbsolutePath))) {
            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String extractBodyFromInputFileLines(List<String> inputFileLines){
        List<String> body = new ArrayList<>();
        int i = 0;
        while (i < inputFileLines.size()){
            if (inputFileLines.get(i).equals("body_start")){
                i++; // skip body_start line
                while (i < inputFileLines.size() && !inputFileLines.get(i).equals("body_end")){
                    body.add(inputFileLines.get(i++));
                }
            }
            i++;
        }

        return body.stream()
                .reduce((currBody, currLine) -> currBody + "\n" + currLine)
                .orElseThrow();
    }

    private Map<String, String> extractKeyValuesFromInputFileLines(List<String> inputFileLines) {
        Map<String,String> keyValues = new HashMap<>();

        int i = 0;
        while ( i < inputFileLines.size()) {
            if (inputFileLines.get(i).equals("values_start")){
                i++;
                while (i < inputFileLines.size() && !inputFileLines.get(i).equals("values_end")){
                    String line = inputFileLines.get(i++);
                    String[] keyAndValue = line.split(":");
                    keyValues.put(keyAndValue[0],keyAndValue[1]);
                }
            }
            i++;
        }
        return keyValues;
    }

    private String extractAddressesFromInputFileLines(List<String> inputFileLines) {
        List<String> addresses = new ArrayList<>();
        int i = 0;
        while ( i < inputFileLines.size()) {
            if (inputFileLines.get(i).equals("addresses_start")){
                i++;
                while (i < inputFileLines.size() && !inputFileLines.get(i).equals("addresses_end")){
                    addresses.add(inputFileLines.get(i++));
                }
            }
            i++;
        }
        return addresses
                .stream()
                .reduce((currAddresses, currAddress ) -> currAddresses + "," + currAddress)
                .orElseThrow();
    }
}
