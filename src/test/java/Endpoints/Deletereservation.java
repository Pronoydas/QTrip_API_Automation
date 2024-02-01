package Endpoints;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Constants.Endpoints;
import Endpoints_Steps.DeleteReservation_Steps;
import Utility.CommonActions;
import Utility.ReadJSON;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;

public class Deletereservation {
	RequestSpecBuilder builder;
	RequestSpecification reqSep;
	ResponseSpecBuilder resBuilder;
	ResponseSpecification resSep;
	AuthenticationScheme authScheme;
	String resID;
	
	@BeforeClass
	  public void setup() throws IOException {
		  DeleteReservation_Steps.updatePayloadData("DeleteReservation_Payload.json");
		  resID=DeleteReservation_Steps.getReservationID();
		  authScheme=RestAssured.oauth2(CommonActions.getToken());
		  builder = new RequestSpecBuilder();
		  builder.setBaseUri(Endpoints.BASE_URL);
		  builder.setBasePath(Endpoints.BASE_PATH);
		  builder.setAuth(authScheme);
		  builder.setContentType(ContentType.JSON);
		  reqSep=builder.build();
		  resBuilder = new ResponseSpecBuilder();
		  resBuilder.expectResponseTime(Matchers.lessThanOrEqualTo(3L), TimeUnit.SECONDS);
		  resSep = resBuilder.build();
	  }
	
	@Test(description = "Delete a Valid Researvtion")
	public void deleteReservation() throws IOException {
		given().spec(reqSep)
		.body(ReadJSON.convertJSON("DeleteReservation_Payload.json"))
		.log().ifValidationFails()
		.when().delete("/reservations/"+resID.trim())
		.then().log().ifError()
		.statusCode(200)
		.body("success", Matchers.is(true));
		
	}
	

	@Test(description = "Delete a Invalid Researvtion")
	public void deleteReservation_Invalid() throws IOException {
		given().spec(reqSep)
		 .body(ReadJSON.convertJSON("Invalid_DeleteReservation_Payload.json"))
		.log().ifValidationFails()
		.when().delete("/reservations/"+resID.trim())
		.then().log().ifError()
		;
		
	}

}
