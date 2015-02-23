package org.jenkinsci.plugins.sonarcompliance.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class UtilitiesTest {
	@Test
	public void testMetricsFromSimpleRule() {
		Set<String> metrics = Utilities
				.getMetricsFromRule("coverage>0");
		assertEquals(1, metrics.size());
		assertTrue(metrics.contains("coverage"));
	}

	@Test
	public void testMetricsFromRepatedMetricRule() {
		Set<String> metrics = Utilities
				.getMetricsFromRule("covergae>=50 && covergae<=70");
		assertEquals(1, metrics.size());
		assertTrue(metrics.contains("covergae"));
	}

	@Test
	public void testMetricsFromNotStartedMetricRule() {
		Set<String> metrics = Utilities
				.getMetricsFromRule("50 < covergae < 70");
		assertEquals(1, metrics.size());
		assertTrue(metrics.contains("covergae"));
	}

	@Test
	public void testMetricsFromWhiteSpacedMetricRule() {
		Set<String> metrics = Utilities
				.getMetricsFromRule("covergae     <    40.50");
		assertEquals(1, metrics.size());
		assertTrue(metrics.contains("covergae"));
	}

	@Test
	public void testMetricsFromMultipleMetricRule() {
		Set<String> metrics = Utilities
				.getMetricsFromRule("critical_vaiolations + major_violations < 20");
		assertEquals(2, metrics.size());
		assertTrue(metrics.contains("critical_vaiolations"));
		assertTrue(metrics.contains("major_violations"));
	}

	@Test
	public void testMetricsFromNoMetricRule() {
		Set<String> metrics = Utilities
				.getMetricsFromRule("45.456");
		assertEquals(0, metrics.size());
	}

	@Test
	public void testMetricsFromEmptyMetricRule() {
		Set<String> metrics = Utilities.getMetricsFromRule("");
		assertEquals(0, metrics.size());
	}

	@Test
	public void testMetricsFromNullMetricRule() {
		Set<String> metrics = Utilities.getMetricsFromRule(null);
		assertEquals(0, metrics.size());
	}

}
