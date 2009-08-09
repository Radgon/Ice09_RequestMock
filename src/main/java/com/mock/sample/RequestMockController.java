package com.mock.sample;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class RequestMockController implements Controller
{
	@Autowired
	protected String localRepo;
	
	@Autowired
	protected XmlTool xml;
	
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String line;
        StringBuilder requestComplete = new StringBuilder();
        while ((line = request.getReader().readLine()) != null) {
        	requestComplete.append(line);
        }
        String requestCompleteString = requestComplete.toString();
        String requestId = xml.extractPayloadAsString(requestCompleteString);
        File requestDirectory = new File(localRepo + requestId);
        String payload = null;
        if (requestDirectory.exists()) {
        	payload = xml.evalTemplateWithPayload(new File(localRepo + requestId), xml.extractPayload(requestCompleteString));
        } else {
        	response.setStatus(500);
        	payload = "error occured: no template found for " + requestId; 
        }
        
        return new ModelAndView("result.jsp", "result", payload);
    }
}