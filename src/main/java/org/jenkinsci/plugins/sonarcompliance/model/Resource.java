package org.jenkinsci.plugins.sonarcompliance.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Ramesh Dara
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource {
    List<MSR> msr;

    public List<MSR> getMsr() {
        return msr;
    }

    public void setMsr(List<MSR> msr) {
        this.msr = msr;
    }

    public String getMetricValue(String metric) {
        for (MSR msrEntry : msr) {
            if (msrEntry.getKey().equals(metric)) {
                return msrEntry.getVal();
            }
        }
        return "";
    }

}
