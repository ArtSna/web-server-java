package org.wsj;

import com.fasterxml.jackson.databind.JsonNode;

public class Body {

	private final JsonNode node;
	
	public Body(JsonNode node) {
		this.node = node;
	}
	
	public boolean isEmpty() {
		return node == null;
	}
	
	public Body get(String fieldName) {
		JsonNode tmpNode = node.get(fieldName);
				
		if(tmpNode == null)
			new NullPointerException("field '" + fieldName + "' on body was not found.").printStackTrace();;
		
		return new Body(tmpNode);
	}
	
	public String getString() {
		return node.asText();
	}
	
	public int getInt() {
		return node.asInt();
	}
	
	public long getLong() {
		return node.asLong();
	}
	
	public boolean getBoolean() {
		return node.asBoolean();
	}
	
	public double getDouble() {
		return node.asDouble();
	}

}
