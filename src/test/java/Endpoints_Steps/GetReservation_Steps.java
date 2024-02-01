package Endpoints_Steps;

import Utility.ReadUpdateJSON;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetReservation_Steps {
	
	
	
    public static void storeReservationId(String key , String value) {
    	ReadUpdateJSON.addNewValue(key, value);
		
	}
	
	public static String extractReservationId(Response res) {
		JsonPath jp = new JsonPath(res.asString());
		String id = jp.get("[0].id").toString();
		return id;
	}

}
