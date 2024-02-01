package Endpoints_Steps;

import Utility.ReadUpdateJSON;

public class GetAdventuredetails_Steps {
	
	public static String extractAdventureId() {
		String advantureId = null;
		advantureId=ReadUpdateJSON.getKeyValue("AdvantureId");
		return advantureId;
	}

}
