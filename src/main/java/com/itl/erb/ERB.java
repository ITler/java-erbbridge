package com.itl.erb;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ERB {
	
	static ScriptEngine jruby = initializeJRuby();

	static ScriptEngine initializeJRuby() {
		try (InputStream bootstrap = ClassLoader.getSystemResourceAsStream("bootstrap.rb")) {
			ScriptEngine jruby = new ScriptEngineManager().getEngineByName("jruby");
			jruby.eval(new InputStreamReader(bootstrap));
			return jruby;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static InputStream openTemplate(String name) {
		return ClassLoader.getSystemResourceAsStream(name);
	}

	public static String render(String template, Map<String, Object> variables) throws Exception {
		try (InputStream stream = openTemplate(template)) {
			return (String) ((Invocable) jruby).invokeFunction("render", stream, variables);
		}
	}

	/**
	 * Render an ERB template wrapped in a ERB layout.
	 */
	public static String render(String template, String layout, Map<String, Object> variables) throws Exception {
		try (InputStream templateStream = openTemplate(template);
				InputStream layoutStream = openTemplate(layout)) {
			return (String) ((Invocable) jruby).invokeFunction(
					"render_with_layout", templateStream, layoutStream, variables);
		}
	}
}