package org.jenkinsci.plugins.sonarcompliance.executors;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.jenkinsci.plugins.sonarcompliance.common.ComplianceConfigurator;
import org.jenkinsci.plugins.sonarcompliance.model.MSR;
import org.jenkinsci.plugins.sonarcompliance.model.Resource;

/**
 * @author Ramesh Dara
 *
 */

public class DifferntialComplianceExecutor extends BaseComplianceExecutor {

    @Override
    protected void processAdditionalSonar(ComplianceConfigurator conf, Resource primaryResource, String metrics) {
        Resource secondaryResource = getResource(conf.getSecondarySonarAPIResourceURL(), conf.getSecondaryProjectKey(),
                metrics);
        calculateVariances(primaryResource, secondaryResource);
    }

    @Override
    protected String getMSRValue(MSR msr) {
        return msr.getVar1();
    }

    private void calculateVariances(Resource primaryResource, Resource secondaryResource) {
        ScriptEngine engine = getJavaScriptEngine();
        for (MSR msr : primaryResource.getMsr()) {
            MSR secondMSR = getMSR(secondaryResource, msr.getKey());
            if (secondMSR != null) {
                try {
                    msr.setVar1(engine.eval(msr.getVal() + " - " + secondMSR.getVal()).toString());
                } catch (ScriptException e) {
                    msr.setVar1("0");
                }
            } else {
                msr.setVar1("0");
            }
        }
    }

    private MSR getMSR(Resource resource, String key) {
        for (MSR msr : resource.getMsr()) {
            if (msr.getKey().equals(key)) {
                return msr;
            }
        }
        return null;
    }

}
