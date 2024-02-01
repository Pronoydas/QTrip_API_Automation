package Endpoints_Steps;

import Utility.ReadJSON;
import Utility.ReadUpdateJSON;

public class DeleteReservation_Steps {
	
private static String userID;
	
	
	private static void getUserID() {
		 userID=ReadUpdateJSON.getKeyValue("UserId");
	}
	
	public static String getReservationID() {
		 return ReadUpdateJSON.getKeyValue("UserId");
	}
	
	public static  void updatePayloadData(String fileName) {
		getUserID();
		ReadJSON.updatePayload("userId", userID, fileName);	
	}

}
