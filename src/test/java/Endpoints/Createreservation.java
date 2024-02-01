package Endpoints;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import Constants.Endpoints;
import Endpoints_Steps.Createreservation_Steps;
import Utility.CommonActions;
import Utility.ReadJSON;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Createreservation {
	RequestSpecBuilder builder;
	RequestSpecification reqSep;
	ResponseSpecBuilder resBuilder;
	ResponseSpecification resSep;
	AuthenticationScheme authScheme;
	String token;
	
		
	
  @BeforeClass
  public void setup() throws IOException {
	  Createreservation_Steps.updatePayloadData("CreateReservation_Payload.json");
	  token =CommonActions.getToken();
	  authScheme=RestAssured.oauth2(token);
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
  @Test(description = "Create Reservation With Valid Details::")
  public void createReservation() throws IOException{
	  given().spec(reqSep)
	  .log().ifValidationFails()
	  .body(ReadJSON.convertJSON("CreateReservation_Payload.json"))
	  .when().post(Endpoints.CREATERESERVATIONAPI)
	  .then().log().ifValidationFails()
	  .statusCode(200)
	  .body("success", Matchers.is(true))
	  ;
  }
  
  @Test(description = "Create Reservation With Invalid Details::")
  public void createReservation_With_Invalid_Data() throws IOException{
	  given().spec(reqSep)
	  .log().ifValidationFails()
	  .body(ReadJSON.convertJSON("Invalid_CreateReservation_Payload.json"))
	  .when().post(Endpoints.CREATERESERVATIONAPI)
	  .then().log().ifValidationFails()
	  .statusCode(400)
	  .body("success", Matchers.is(false))
	  .body("message", Matchers.equalToIgnoringCase("Bad token or user no longer exists. Try Registering and logging in again."))
	  ;
  }

}
