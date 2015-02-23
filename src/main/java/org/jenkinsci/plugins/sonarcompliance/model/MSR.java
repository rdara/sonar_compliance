package org.jenkinsci.plugins.sonarcompliance.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Ramesh Dara
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MSR {
    String key;
    String val;
    String frmt_val;
    String alert;
    String alert_text;
    String trend;
    String var;
    String var1;
    String var2;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getFrmt_val() {
        return frmt_val;
    }

    public void setFrmt_val(String frmt_val) {
        this.frmt_val = frmt_val;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert_text() {
        return alert_text;
    }

    public void setAlert_text(String alert_text) {
        this.alert_text = alert_text;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public String getVar2() {
        return var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }

}
