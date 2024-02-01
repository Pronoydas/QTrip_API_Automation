package Utility;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ReadJSON {
	
	public static String baseFilePath="C:\\Users\\prono\\eclipse-workspace\\QTripAPI_Automation\\src\\test\\resources\\Payloads\\";
	private static ObjectMapper objectMapper;
	
	public static String convertJSON(String fileName) throws IOException {
		String path = baseFilePath+fileName;
		byte[] arr = Files.readAllBytes(Paths.get(path));
		String payload= new String(arr);
		return payload;
	}
	
	
	public static void updatePayload(String key , String value,String fileName){
		String filepath=baseFilePath+fileName;
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

}
