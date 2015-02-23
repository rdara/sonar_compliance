package org.jenkinsci.plugins.sonarcompliance.executors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.jenkinsci.plugins.sonarcompliance.common.ComplianceConfigurator;
import org.jenkinsci.plugins.sonarcompliance.common.Utilities;
import org.jenkinsci.plugins.sonarcompliance.interfaces.Configurator;
import org.jenkinsci.plugins.sonarcompliance.interfaces.Executor;
import org.jenkinsci.plugins.sonarcompliance.model.ComplianceRule;
import org.jenkinsci.plugins.sonarcompliance.model.MSR;
import org.jenkinsci.plugins.sonarcompliance.model.Resource;
import org.jenkinsci.plugins.sonarcompliance.model.Result;

/**
 * @author Ramesh Dara
 *
 */

@SuppressWarnings("restriction")
public abstract class BaseComplianceExecutor implements Executor {

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean execute(Configurator conf) {
        return execute((ComplianceConfigurator) conf);
    }

    public boolean execute(ComplianceConfigurator conf) {
        String metrics = Utilities.getConcatenatedString(conf.getAllMetrics(), ",");
        Resource primaryResource = getResource(conf.getPrimarySonarAPIResourceURL(), conf.getPrimaryProjectKey(),
                metrics);
        processAdditionalSonar(conf, primaryResource, metrics);

        ScriptEngine engine = getJavaScriptEngine();
        for (ComplianceRule rule : conf.getAllComplianceRules().values()) {
            try {
                for (MSR msr : primaryResource.getMsr()) {
                    conf.getLogger().println("msr.getKey() = " + msr.getKey());
                    conf.getLogger().println("msr.getVal() = " + getMSRValue(msr));
                    conf.getLogger().println("msr.getVar1 = " + msr.getVar1());

                    engine.eval(msr.getKey() + " = " + getMSRValue(msr));
                }
                conf.getLogger().println("rule.getRule() = " + rule.getRule());
                Boolean result = (Boolean) engine.eval(rule.getRule());
                conf.getLogger().println("result = " + result);
                rule.setResult(result == Boolean.TRUE ? Result.SUCCESS : Result.FAILURE);
                conf.getLogger().println("rule.getResult()  = " + rule.getResult());
            } catch (ScriptException e) {
                rule.setResult(Result.EXCEPTION);
                rule.setException(
                        e,
                        rule.getRule()
                                + " couldn't be evaluated. Only JavaScript expressions with sonar supported metrics as variables are supported.");
                conf.getLogger().println("SC EXCEPTION : " + e.getMessage());
                conf.getLogger()
                        .println(
                                rule.getRule()
                                        + " couldn't be evaluated. Only JavaScript expressions with sonar supported metrics as variables are supported.");
            }
        }
        return true;
    }

    protected void processAdditionalSonar(ComplianceConfigurator conf, Resource primaryResource, String metrics) {
    }

    protected Resource getResource(String sonarUrl, String projectKey, String metrics) {
        UriBuilder uriBuilder = UriBuilder.fromPath(sonarUrl);
        uriBuilder.queryParam("resource", projectKey);
        uriBuilder.queryParam("metrics", metrics);
        appendQueryParams(uriBuilder);
        Resource[] resources = getRSClient().target(uriBuilder.build()).request().get(Resource[].class);
        return resources[0];
    }

    protected void appendQueryParams(UriBuilder uriBuilder) {
    }

    protected String getMSRValue(MSR msr) {
        return msr.getVal();
    }

    protected ScriptEngine getJavaScriptEngine() {
        return new ScriptEngineManager().getEngineByName("JavaScript");
    }

    protected Client getRSClient() {
        return ClientBuilder.newClient().register(JacksonFeature.class);
    }

}
