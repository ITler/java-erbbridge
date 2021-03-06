package com.itl.erb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.itl.erb.ERB;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ERBTest {

	private static Map<String, String> testTemplates;

	private static Map<String, Object> variables;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		assignTemplatesAndDesiredResults();
		assignTemplateVariables();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private static void assignTemplatesAndDesiredResults() {
		testTemplates = new HashMap<>();
		testTemplates.put("plain_template", "erbtest.erb");
		testTemplates.put("plain_result", "User:JUnit");

		testTemplates.put("nested_template", "erbtestnested.erb");
		testTemplates.put("nested_result",
				String.format("%s;Nested:NestedTest%s",
						testTemplates.get("plain_result"), 
						"\n123")); // loop test
	}

	private static void assignTemplateVariables() {
		variables = new HashMap<>();
		variables.put("@user", "JUnit");
		variables.put("@loopcount", 3);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInitializeJRuby() {
		assertNotNull(ERB.jruby);
		assertSame(org.jruby.embed.jsr223.JRubyEngine.class,
				ERB.jruby.getClass());
	}

	@Test
	public void testRenderTemplate() {
		try {
			assertEquals(testTemplates.get("plain_result"),
					ERB.render(testTemplates.get("plain_template"), variables)
							.trim());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception while testing");
		}
	}

	@Test
	public void testRenderTemplateWithNestedTemplate() {
		try {
			assertEquals(testTemplates.get("nested_result"),
					ERB.render(testTemplates.get("nested_template"), variables)
							.trim());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception while testing");
		}
	}

}
