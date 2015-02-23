package org.jenkinsci.plugins.sonarcompliance.model;

import java.util.Set;

/**
 * @author Ramesh Dara
 *
 */

public class ComplianceRule {
    private String key;
    private String rule;
    private Result result = Result.IGNORED;
    private Set<String> failedMetrics;
    private Set<String> metrics;
    private Exception exception;
    private String exceptionMessage;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Set<String> getFailedMetrics() {
        return failedMetrics;
    }

    public void setFailedMetrics(Set<String> failedMetrics) {
        this.failedMetrics = failedMetrics;
    }

    public Set<String> getMetrics() {
        return metrics;
    }

    public void setMetrics(Set<String> metrics) {
        this.metrics = metrics;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception, String exceptionMessage) {
        this.exception = exception;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
