package org.wsj;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Parameter {

	private String path;
	private Map<String, String> parameters = new HashMap<>();
	
	public Parameter(URI uri) {
		try {
			System.out.println(uri.toString());
			String[] splited = uri.toString().split("\\?");
			this.path = splited[0];
			
			if(splited.length == 1)
				return;
			
			String[] splited2 = splited[1].split("&");
			for(String s : splited2) {
				String[] arg = s.split("=");
				parameters.put(arg[0], arg[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public String get(String name) {
		return parameters.get(name);
	}
	
	public String getUrlPath() {
		return path;
	}
	
	public Map<String, String> getMap() {
		return parameters;
	}
}
