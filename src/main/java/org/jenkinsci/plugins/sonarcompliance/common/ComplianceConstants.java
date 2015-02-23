package org.jenkinsci.plugins.sonarcompliance.common;

/**
 * @author Ramesh Dara
 *
 */

public interface ComplianceConstants {
    String NAME = "name";
    String PRIMARY_SONAR_URL = "primary_sonar_url";
    String PRIMARY_PROJECT_KEY = "primary_project_key";
    String SECONDARY_SONAR_URL = "secondary_sonar_url";
    String SECONDARY_PROJECT_KEY = "secondary_project_key";
    String COMPLIANCE_RULES = "compliance_rules"; //new_coverage>=70
    String DYNAMIC_COMPLIANCE = "dynamic_compliance";
    String ADDITIONAL_SONAR = "additional_sonar";
}
