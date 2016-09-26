package com.tec.datos1.tron.client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageSerial {
	
	public static NetMessage getInfoFromJsonString(String jsonString) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		NetMessage message = mapper.readValue(jsonString, NetMessage.class);
		return message;
		
	}
	
	public static String getJsonStringFromMessage(NetMessage message) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(message);
		return jsonString;
	}
	
}
