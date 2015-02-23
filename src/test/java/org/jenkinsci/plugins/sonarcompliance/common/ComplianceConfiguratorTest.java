package org.jenkinsci.plugins.sonarcompliance.common;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ComplianceConfiguratorTest {

	ComplianceConfigurator complianceConfigurator;

	@Before
	public void initialization() {
		String[] strArgs = { "key::value" };
		complianceConfigurator = new ComplianceConfigurator(strArgs, "::");
		Map<String, String> properties = complianceConfigurator.getProperties();
		properties.put(ComplianceConstants.PRIMARY_SONAR_URL,
				"http://primarysonar:9000/");
		properties.put(ComplianceConstants.SECONDARY_SONAR_URL,
				"http://secondarysonar:9000/dontcare/file/path");

	}

	// Test URL resolving
	@Test
	public void getPrimarySonarAPIResourceURL() {
		assertEquals("http://primarysonar:9000/api/resources",
				complianceConfigurator.getPrimarySonarAPIResourceURL());
	}

	@Test
	public void getSecondarySonarAPIResourceURL() {
		assertEquals("http://secondarysonar:9000/api/resources",
				complianceConfigurator.getSecondarySonarAPIResourceURL());
	}

	// Tests for metric key to represent the user compliance rule
	/*
	@Test
	public void getMetricKeySimple() {
		assertEquals("coverage",
				complianceConfigurator.getMetricKey("coverage>0").toString());
	}

	@Test
	public void getMetricKeyComposite() {
		assertEquals(
				"critical_vaiolations_major_violations",
				complianceConfigurator.getMetricKey(
						"critical_vaiolations + major_violations < 20")
						.toString());
	}

	@Test
	public void getMetricKeyCompositeReverseOrder() {
		assertEquals(
				"major_violations_critical_vaiolations",
				complianceConfigurator.getMetricKey(
						"major_violations + critical_vaiolations < 20")
						.toString());
	}
	
	@Test
	public void getMetricKeyEmpty() {
		assertEquals("", complianceConfigurator.getMetricKey("").toString());
	}

	@Test
	public void getMetricKeyNull() {
		assertEquals("", complianceConfigurator.getMetricKey(null).toString());
	}
	*/
	// Test unique metric keys obtained from the

}
