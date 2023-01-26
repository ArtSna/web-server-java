package org.wsj;

import java.util.HashMap;
import java.util.Map;

public class Route {

	private String path;
	
	private Map<Method, WebHandler> handlers = new HashMap<>();
	
	public Route(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	
	public Map<Method, WebHandler> getHandlers() {
		return handlers;
	}
	
	public Route putHandler(Method method, WebHandler handler) {
		handlers.put(method, handler);
		return this;
	}
	
	public enum Method {
		GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS;
	}
	
}
