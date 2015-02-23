package org.jenkinsci.plugins.sonarcompliance.common;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Ramesh Dara
 *
 */

public class Utilities {

    public static final String NEW_LINE = System.getProperty("line.separator");

    private static Pattern rullMetricPattern = Pattern.compile("([A-Za-z_]*)");

    public static Set<String> getMetricsFromRule(String input) {
        Set<String> setOfMetrics = new HashSet<String>();
        if (StringUtils.isNotEmpty(input)) {
            Matcher m = rullMetricPattern.matcher(input.trim());
            while (m.find()) {
                if (StringUtils.isNotEmpty(m.group(1))) {
                    setOfMetrics.add(m.group(1));
                }
            }
        }
        return setOfMetrics;
    }

    public static String getConcatenatedString(Set<String> values, String separator) {
        StringBuilder concatenatedBuilder = new StringBuilder("");
        boolean bFirst = true;
        for (String str : values) {
            if (bFirst) {
                bFirst = false;
            } else {
                concatenatedBuilder.append(separator);
            }
            concatenatedBuilder.append(str);
        }
        return concatenatedBuilder.toString();
    }
}
