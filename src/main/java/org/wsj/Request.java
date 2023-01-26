package org.wsj;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

public class Request {

	private Status status;
	private Header header;
	private Body body;
	private Parameter parameter;
	
	public Request(HttpExchange httpExchange) {
		this.status = Status.OK;
		this.parameter = new Parameter(httpExchange.getRequestURI());
		this.header = new Header(httpExchange.getRequestHeaders());
		try {
			body = new Body(new ObjectMapper().reader().createParser(httpExchange.getRequestBody().readAllBytes()).readValueAsTree());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public int getStatusCode() {
		return status.code();
	}
	
	public Status getStatus() {
		return status;
	}
	
	public Parameter getParameters() {
		return parameter;
	}
	
	public String getUrlPath() {
		return parameter.getUrlPath();
	}
	
	public Body getBody() {
		if(body.isEmpty()) {
			new NullPointerException("body does not have any content.").printStackTrace();
		}
		
		return body;
	}
	
	public Header getHeaders() {
		return header;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	public enum Status {
		OK(200),
		CREATED(201), 
		NOT_FOUND(404);
		
		private final int code;
		
		Status(int code) {
			this.code = code;
		}
		
		public int code() {
			return code;
		}
		
	}
}
