package org.jenkinsci.plugins.sonarcompliance;

import hudson.model.ParameterValue;
import hudson.model.ParametersAction;

import java.util.Arrays;
import java.util.List;

public class SonarComplainceParametersAction extends ParametersAction {
    private String name = "";

    public SonarComplainceParametersAction(String name, List<ParameterValue> parameters) {
        super(parameters);
        this.name = name;
    }

    public SonarComplainceParametersAction(String name, ParameterValue... parameters) {
        this(name, Arrays.asList(parameters));
    }

    public String getDisplayName() {
        return name + "_" + super.getDisplayName();
    }

    public String getUrlName() {
        return name + "_" + super.getDisplayName();
    }
}
