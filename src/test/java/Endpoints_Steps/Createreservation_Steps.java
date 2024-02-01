package Endpoints_Steps;

import Utility.ReadJSON;
import Utility.ReadUpdateJSON;


public class Createreservation_Steps {
	private static String adventureId;
	private static String userID;
	
	
	private static void getUserID() {
		 userID=ReadUpdateJSON.getKeyValue("UserId");
	}
	
	private static void getAdventureID() {
		 adventureId=ReadUpdateJSON.getKeyValue("AdvantureId");
	}
	
	public static  void updatePayloadData(String fileName) {
		getUserID();
		getAdventureID();
		ReadJSON.updatePayload("adventure", adventureId, fileName);
		ReadJSON.updatePayload("userId", userID, fileName);	
	}
	

}
