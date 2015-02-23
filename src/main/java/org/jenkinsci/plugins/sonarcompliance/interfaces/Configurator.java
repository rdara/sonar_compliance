package org.jenkinsci.plugins.sonarcompliance.interfaces;

import org.jenkinsci.plugins.sonarcompliance.common.PropertyException;

/**
 * @author Ramesh Dara
 *
 */

public interface Configurator {
    void addProperty(String key, String value) throws PropertyException;

    void addPropertyPair(String keyValue, String separator) throws PropertyException;
}
