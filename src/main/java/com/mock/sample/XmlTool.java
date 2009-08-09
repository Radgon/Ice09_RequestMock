package com.mock.sample;

import java.io.File;

public interface XmlTool {
	
	Object extractPayload(String name);
	String extractPayloadAsString(String name);
	String evalTemplateWithPayload(File root, Object object);

}
