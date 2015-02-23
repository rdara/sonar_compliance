package org.jenkinsci.plugins.sonarcompliance.executors;

import org.jenkinsci.plugins.sonarcompliance.common.ComplianceConfigurator;
import org.jenkinsci.plugins.sonarcompliance.interfaces.Executor;

/**
 * @author Ramesh Dara
 *
 */

public class ComplianceExecutorFactory {
    public static Executor getComplianceExecutor(ComplianceConfigurator config) {
        if (config.isAdditionalSonar()) {
            return new DifferntialComplianceExecutor();
        } else if (config.isDynamicCompliance()) {
            return new DynamicComplianceExecutor();
        } else {
            return new StaticComplianceExecutor();
        }
    }
}
