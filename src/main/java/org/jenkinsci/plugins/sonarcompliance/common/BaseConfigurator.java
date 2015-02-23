package org.jenkinsci.plugins.sonarcompliance.common;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.jenkinsci.plugins.sonarcompliance.interfaces.Configurator;

/**
 * @author Ramesh Dara
 *
 */
public abstract class BaseConfigurator implements Configurator {

    protected void processProperty(String key, String value) throws PropertyException {
        if (value != null && value.contains("github.") && value.contains("/blob/")) {
            value = value.replace("/blob/", "/raw/");
        }
        if (validate(key, value)) {
            getProperties().put(key, value);
        }
    }

    public boolean validate(String key, String value) throws PropertyException {
        return true;
    }

    public Set<String> getElements(String input) {
        Set<String> returnSet = new LinkedHashSet<String>();
        input = input.trim();
        if (input.length() > 0) {
            String[] cats = input.split(",");

            for (String cat : cats) {
                if (cat.trim().length() > 0) {
                    returnSet.add(cat.trim());
                }
            }
        }
        return returnSet;
    }

    public abstract Map<String, String> getProperties();

    @Override
    public void addProperty(String key, String value) throws PropertyException {
        processProperty(key, value);
    }

    @Override
    public void addPropertyPair(String keyValue, String separator) throws PropertyException {
        String[] strKeyValues = keyValue.split(separator);
        if (strKeyValues.length == 2) {
        } else {
            throw new PropertyException(keyValue, PropertyException.TYPE.FORMAT);
        }
        processProperty(strKeyValues[0].toLowerCase(), strKeyValues[1]);
    }

}
