package Utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ReadUpdateJSON {
	
	private final static  String filepath="C:\\Users\\prono\\eclipse-workspace\\QTripAPI_Automation\\src\\test\\resources\\CommonData\\Data.json";
	private static ObjectMapper objectMapper;
	
   
	//this method can be use for adding , Updating JSON Data .(Works for String Datatype)
	public static void addNewValue(String key, String value){
		try {
		byte[] json=Files.readAllBytes(Paths.get(filepath));
		objectMapper= new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);
		JsonNode jn=objectMapper.readTree(json);
		((ObjectNode)jn).put(key, value);
		objectMapper.writeValue(Paths.get(filepath).toFile(), jn);	
		}catch(Exception e) {
			e.getMessage();
		}
	}
	//this method can be use for adding , Updating JSON Data .(Works for Integer Datatype)
	public static void addNewValue(String key, Integer value){
		try {
		byte[] json=Files.readAllBytes(Paths.get(filepath));
		objectMapper= new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);
		JsonNode jn=objectMapper.readTree(json);
		((ObjectNode)jn).put(key, value);
		objectMapper.writeValue(Paths.get(filepath).toFile(), jn);	
		}catch(Exception e) {
			e.getMessage();
		}
	}
	
	
	public static String getKeyValue(String keyName) {
		String keyValue=null;
		try {
			byte[] json=Files.readAllBytes(Paths.get(filepath));
			objectMapper= new ObjectMapper();
			JsonNode jn=objectMapper.readTree(json);
			keyValue=jn.path(keyName).asText();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keyValue;
		
		
	}
	
	
	
	

	

}
