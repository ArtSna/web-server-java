package org.wsj;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map.Entry;

import org.wsj.Route.Method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

public class WebServer {
	
	HttpServer server;
	
	public WebServer(int port) {
		this(new InetSocketAddress(port));
	}
	
	public WebServer(InetSocketAddress addr) {
		try {
			server = HttpServer.create(addr, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		for(Route route : Router.getRoutes()) {
			server.createContext(route.getPath(), (httpExchange) -> {
		    	Request request = new Request(httpExchange); 						
				boolean haveResponse = false;

				for(Entry<Method, WebHandler> handler : route.getHandlers().entrySet()) {
				     if (httpExchange.getRequestMethod().equalsIgnoreCase(handler.getKey().toString())) {
				    	 haveResponse = true;
				    	 Object responseObject = handler.getValue().response(request);
				    	 String response = buildResponse(responseObject);
				    	 writeResponse(httpExchange, request, response);
				     }
				}
				
				if(!haveResponse) {
					request.setStatus(Request.Status.NOT_FOUND);
					writeResponse(httpExchange, request, null);
				}
			});
		}
		
		server.start();
		System.out.println("Server started."); 
	}

	public void stop(int delay) {
		server.stop(delay);
	}
	
	private void writeResponse(HttpExchange httpExchange, Request request, String response) throws IOException {
		if(response == null)
			response = "";
		
        OutputStream outputStream = httpExchange.getResponseBody();
        httpExchange.sendResponseHeaders(request.getStatusCode(), response.length());
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
	}
	
	private String buildResponse(Object responseObject) {
		if(responseObject instanceof String) 
			return responseObject.toString();
		
		try {
			return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(responseObject);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
