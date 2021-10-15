package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;

import java.util.Map;

/**
 * The type Template engine.
 */
public class TemplateEngine {
    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) {
        Map<String, String> providedKeyValues = client.getProvidedKeyValues(); // example: name => Alica, job => Software Engineer
        Map<String, String> keyValuesInTemplate = template.getKeyValues(); // example: name => null, job => null
        String generatedMessage = template.getBody(); // initial template body
        int providedKeyValueCounter = 0; // all placeholders must be provided, so counter is used to keep track of them
        for (String providedKey : providedKeyValues.keySet()){
            if (keyValuesInTemplate.containsKey(providedKey)) {
                generatedMessage = generatedMessage.replace("#{" +providedKey + "}", providedKeyValues.get(providedKey));
                providedKeyValueCounter++;
            }
        }
        if (keyValuesInTemplate.size() == providedKeyValueCounter){
            return generatedMessage;
        } else {
            throw new RuntimeException("not each key is given");
        }
    }
}
