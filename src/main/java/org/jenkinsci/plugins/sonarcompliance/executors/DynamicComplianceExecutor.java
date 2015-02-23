package org.jenkinsci.plugins.sonarcompliance.executors;

import javax.ws.rs.core.UriBuilder;

import org.jenkinsci.plugins.sonarcompliance.model.MSR;

/**
 * @author Ramesh Dara
 *
 */
public class DynamicComplianceExecutor extends BaseComplianceExecutor {

    @Override
    protected void appendQueryParams(UriBuilder uriBuilder) {
        uriBuilder.queryParam("includetrends", "true");
    }

    @Override
    protected String getMSRValue(MSR msr) {
        return msr.getVar1();
    }

}
