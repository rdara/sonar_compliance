package org.jenkinsci.plugins.sonarcompliance.common;

import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.jenkinsci.plugins.sonarcompliance.SonarComplianceBuilder;
import org.jenkinsci.plugins.sonarcompliance.model.ComplianceRule;

/**
 * @author Ramesh Dara
 *
 */

public class ComplianceConfigurator extends BaseConfigurator {

    private Map<String, String> configProperties = new ConcurrentHashMap<String, String>();
    private PrintStream logger;
    private boolean additionalSonar = false;
    private boolean dynamicCompliance = false;
    private Map<String, ComplianceRule> complianceRules = new ConcurrentHashMap<String, ComplianceRule>();

    public ComplianceConfigurator(SonarComplianceBuilder builder) {
        try {
            addProperty(ComplianceConstants.PRIMARY_SONAR_URL, builder.getPrimarySonarURL());
            addProperty(ComplianceConstants.PRIMARY_PROJECT_KEY, builder.getPrimaryProjectKey());
            if (builder.getAdditionalSonar()) {
                addProperty(ComplianceConstants.SECONDARY_SONAR_URL, builder.getSecondarySonarURL());
                addProperty(ComplianceConstants.SECONDARY_PROJECT_KEY, builder.getSecondaryProjectKey());
                setAdditionalSonar(true);
            }
            addProperty(ComplianceConstants.DYNAMIC_COMPLIANCE, builder.getSonarComparison().toString());
            setDynamicCompliance(builder.getSonarComparison());
            addProperty(ComplianceConstants.COMPLIANCE_RULES, builder.getComplianceRules());
            addProperty(ComplianceConstants.NAME, builder.getName());

            generateComplianceRules();
        } catch (Exception e) {

        }
    }

    public ComplianceConfigurator(String[] args, String separator) {
        processArguments(args, separator);
        generateComplianceRules();
    }

    private int processArguments(String[] args, String separator) {
        int exitCode = 1;
        try {
            if (args != null) {
                for (String strArg : args) {
                    try {
                        addPropertyPair(strArg, separator);
                    } catch (Exception e) {
                        exitCode = 1;
                    }
                }
                exitCode = 0;
            }
        } catch (Exception e) {
            exitCode = 1;
        } finally {
        }
        return exitCode;
    }

    public String getSonarURL() {
        return getProperties().get(ComplianceConstants.PRIMARY_SONAR_URL);
    }

    @Override
    public Map<String, String> getProperties() {
        // TODO Auto-generated method stub
        return configProperties;
    }

    public Map<String, ComplianceRule> getAllComplianceRules() {
        return complianceRules;
    }

    private void generateComplianceRules() {
        complianceRules = new ConcurrentHashMap<String, ComplianceRule>();
        String rules = getProperties().get(ComplianceConstants.COMPLIANCE_RULES);
        String rulePrefix = getRuleTokenPrefix();
        int i = 1;
        if (StringUtils.isNotBlank(rules)) {
            for (String rule : rules.split(",")) {
                ComplianceRule complianceRule = new ComplianceRule();
                complianceRule.setKey(rulePrefix + i++);
                complianceRule.setRule(rule);
                complianceRule.setMetrics(Utilities.getMetricsFromRule(rule));
                complianceRules.put(complianceRule.getKey(), complianceRule);
            }
        }
    }

    /**
     * The parameter key that differentiates each rule.
     * The key is of the format
     * 			SC_{user_provided name}_{squence_number}
     * This helper function provides the prefix, SC_{user_provided name}
     * @return
     */
    private String getRuleTokenPrefix() {
        String name = getProperties().get(ComplianceConstants.NAME);
        return "SC_" + (StringUtils.isBlank(name) ? "" : name + "_");
    }

    public Set<String> getAllComplianceRuleKeys() {
        return getAllComplianceRules().keySet();
    }

    public Set<String> getAllMetrics() {
        Set<String> setOfMetrics = new HashSet<String>();
        for (ComplianceRule rule : getAllComplianceRules().values()) {
            setOfMetrics.addAll(rule.getMetrics());
        }
        return setOfMetrics;
    }

    /**
     * The Metric key concatanates all the unique keys found in the rule.
     * 
     * @param input
     * @return
    public StringBuilder getMetricKey(String input){
    	StringBuilder result = new StringBuilder();
    	if(StringUtils.isNotEmpty(input)){
            Matcher m = rullMetricPattern.matcher(input.trim());
            Map<String,String> metricKeys = new LinkedHashMap<String, String>();
            while(m.find()) {
            	if(StringUtils.isNotEmpty(m.group(1))){
            		metricKeys.put(m.group(1), m.group(1));
            	}
            }
            boolean bFirstOne = true;
        	for(String metric : metricKeys.keySet()){
        		if(bFirstOne){
        			bFirstOne = false;
        		} else {
        			result.append("_");
        		}
        		result.append(metric);
        	}
    	}
    	return result;
    }
    */

    public String getPrimarySonarAPIResourceURL() {
        return getResolvedURL(getProperties().get(ComplianceConstants.PRIMARY_SONAR_URL), "/api/resources");
    }

    public String getSecondarySonarAPIResourceURL() {
        return getResolvedURL(getProperties().get(ComplianceConstants.SECONDARY_SONAR_URL), "/api/resources");
    }

    public String getPrimaryProjectKey() {
        return getProperties().get(ComplianceConstants.PRIMARY_PROJECT_KEY);
    }

    public String getSecondaryProjectKey() {
        return getProperties().get(ComplianceConstants.SECONDARY_PROJECT_KEY);
    }

    private String getResolvedURL(String url, String filePath) {
        try {
            return new URL(new URL(url), filePath).toString();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
        }
        return "";
    }

    public PrintStream getLogger() {
        return logger;
    }

    public void setLogger(PrintStream logger) {
        this.logger = logger;
    }

    public boolean isAdditionalSonar() {
        return additionalSonar;
    }

    public void setAdditionalSonar(boolean additionalSonar) {
        this.additionalSonar = additionalSonar;
    }

    public boolean isDynamicCompliance() {
        return dynamicCompliance;
    }

    public void setDynamicCompliance(boolean dynamicCompliance) {
        this.dynamicCompliance = dynamicCompliance;
    }

}
