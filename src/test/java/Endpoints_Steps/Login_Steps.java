package Endpoints_Steps;

import Utility.ReadJSON;
import Utility.ReadUpdateJSON;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Login_Steps {
	public static String userEmail;
	public static String pwd ;
	private static JsonPath jsonpath;
	
	
	private static void readLoginDetails() {
		userEmail=ReadUpdateJSON.getKeyValue("Email");
		pwd = ReadUpdateJSON.getKeyValue("password");
		
	}
	
	public static void preparedPayload(String fileName) {
		readLoginDetails();
		ReadJSON.updatePayload("email", userEmail, fileName);
		ReadJSON.updatePayload("password", pwd, fileName);
		
	}
	
	public static String getToken(Response res) {
		jsonpath=new JsonPath(res.asString());
		String token =jsonpath.get("data.token");
		return token;
		
	}
	
	public static String getUserID(Response res) {
		jsonpath=new JsonPath(res.asString());
		String token =jsonpath.get("data.id");
		return token;
		
	}
	
	

}
