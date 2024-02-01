package Endpoints;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import Constants.Endpoints;
import Endpoints_Steps.Getadventure_Steps;
import Utility.ReadUpdateJSON;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Getadventure_Test {
	
		RequestSpecBuilder builder;
		RequestSpecification reqSep;
		ResponseSpecBuilder resBuilder;
		ResponseSpecification resSep;
		String adventureID;
			
		
	  @BeforeClass
	  public void setup() throws IOException {
		  builder = new RequestSpecBuilder();
		  builder.setBaseUri(Endpoints.BASE_URL);
		  builder.setBasePath(Endpoints.BASE_PATH);
		  builder.setContentType(ContentType.JSON);
		  reqSep=builder.build();
		  resBuilder = new ResponseSpecBuilder();
		  resBuilder.expectStatusCode(200);
		  resBuilder.expectResponseTime(Matchers.lessThanOrEqualTo(5L), TimeUnit.SECONDS);
		  resSep = resBuilder.build();  
	  }
	  @AfterClass
	  public void afterClassMethod() {
		  ReadUpdateJSON.addNewValue("AdvantureId", adventureID);
	  }
	  
	  @Test(description = "Search AdventureDetais for a city")
	  public void getAdventureDetails() {
		 Response res= given().spec(reqSep)
		  .log().ifValidationFails()
		  .queryParam("city", "bengaluru")
		  .when()
		  .get(Endpoints.GETADVENTUREAPI)
		  .then().spec(resSep)
		  .assertThat()
		  .body("[0].name", Matchers.containsString("Niaboytown"))
		  .body("[3].name", Matchers.containsString("Bageorge With Nonshi Harbour"))
		  .body("[4].costPerHead", Matchers.lessThanOrEqualTo(4143))
		  .extract().response();
		 adventureID=Getadventure_Steps.getAdventureId(res);
		 }
	  
	  @Test(description = "Search AdventureDetais for a Invalid city")
	  public void getAdventureDetails_With_Invalid_City() {
		  given().spec(reqSep)
		  .log().ifValidationFails()
		  .queryParam("city", "Natungram")
		  .when()
		  .get(Endpoints.GETADVENTUREAPI)
		  .then()
		  .statusCode(400)
		  .assertThat()
		  .body("message", Matchers.containsString("Natungram"))
		  ;
		 }
	  @Test(description = "Search AdventureDetais for a city and Advanture Name")
	  public void getAdventureDetails_With_Adventure_name() {
	 given().spec(reqSep)
		  .log().ifValidationFails()
		  .queryParam("city", "bengaluru")
		  .queryParam("q", "Niaboytown")
		  .when()
		  .get(Endpoints.GETADVENTUREAPI)
		  .then().spec(resSep)
		  .assertThat()
		  .body("[0].name", Matchers.containsString("Niaboytown"))
		  .body("[0].costPerHead", Matchers.lessThanOrEqualTo(4143))
		  ;
		 }
	  @Test(description = "Search AdventureDetais for a Invalid city with Adventure Details")
	  public void getAdventureDetails_With_Invalid_City_And_Adventure() {
		  given().spec(reqSep)
		  .log().ifValidationFails()
		  .queryParam("city", "Natungram")
		  .queryParam("q", "Niaboytown")
		  .when()
		  .get(Endpoints.GETADVENTUREAPI)
		  .then()
		  .statusCode(400)
		  .assertThat()
		  .body("message", Matchers.containsString("Natungram"))
		  ;
		 }
	  
	  


	}

	
