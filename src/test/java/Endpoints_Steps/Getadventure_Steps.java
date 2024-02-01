package Endpoints_Steps;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Getadventure_Steps {
	
	
	public static String getAdventureId(Response res) {
		 JsonPath jp = new JsonPath(res.asString());
         return jp.get("[0].id").toString();
		
	}
	
	

}
