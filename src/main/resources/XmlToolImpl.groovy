import java.io.File;import groovy.text.SimpleTemplateEngine;import com.mock.sample.XmlTool;public class XmlToolImpl implements XmlTool {	String extractPayloadAsString(String name) {		extractPayload(name).name()	}	Object extractPayload(String name) {		new XmlSlurper().parseText(name).Body.children()[0]	}	String evalTemplateWithPayload(File root, Object node) {		// first pass		def file = new File("${root.getAbsolutePath()}/template.groovy")		def result = evalStep(file, node).trim()		if (result[0] == ">") {			// second pass			file = new File("${root.getAbsolutePath()}/${result[1..-1]}.groovy");			result = evalStep(file, node);		}		result.trim()	}	String evalStep(File file, Object node) {		def engine = new SimpleTemplateEngine()		def binding = ["in":node]		def template = engine.createTemplate(file.getText()).make(binding)		template.toString()	}}
