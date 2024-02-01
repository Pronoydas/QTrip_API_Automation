package Endpoints;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Constants.Endpoints;
import Endpoints_Steps.Createreservation_Steps;
import Endpoints_Steps.GetReservation_Steps;
import Utility.CommonActions;
import Utility.ReadUpdateJSON;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;

public class GetReservation {
	RequestSpecBuilder builder;
	RequestSpecification reqSep;
	ResponseSpecBuilder resBuilder;
	ResponseSpecification resSep;
	AuthenticationScheme authScheme;
	String reservationId;
	
	@BeforeClass
	  public void setup() throws IOException {
		  Createreservation_Steps.updatePayloadData("CreateReservation_Payload.json");
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
	
	@AfterClass
	public void afterClass() {
		GetReservation_Steps.storeReservationId("ReservationID", reservationId);
	}
	
	@Test 
	public void getReseravtion() throws IOException{
		Response res = given().spec(reqSep)
				.queryParam("id", ReadUpdateJSON.getKeyValue("UserId"))
				.when().get(Endpoints.RESERVATIONAPI)
				.then().spec(resSep)
				.statusCode(200)
				.body("[0].adventure", Matchers.equalToIgnoringCase("2447910730")).extract().response();
		reservationId=GetReservation_Steps.extractReservationId(res);
		    
		
	}
	@Test 
	public void getReseravtion_with_Invalid_Dtails() throws IOException{
		 given().spec(reqSep)
				.queryParam("id", "89789")
				.when().get(Endpoints.RESERVATIONAPI)
				.then().spec(resSep)
				.statusCode(400)
				;
		    
		
	}

	
	
}
