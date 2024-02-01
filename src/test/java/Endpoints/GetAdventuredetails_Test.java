package Endpoints;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Constants.Endpoints;
import Endpoints_Steps.GetAdventuredetails_Steps;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;

public class GetAdventuredetails_Test {
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
	  adventureID=GetAdventuredetails_Steps.extractAdventureId();
  }
  
  @Test(description = "Search for Adventure with valid Id")
  public void search_For_Adventure() {
	  given().spec(reqSep)
	  .log().ifValidationFails()
	  .queryParam("adventure", adventureID)
	  .when().get(Endpoints.GETADVENTUREDETAILSAPI)
	  .then().spec(resSep)
	  .body("name", Matchers.equalToIgnoringCase("Niaboytown"));
	  
  }

  @Test(description = "Search for Adventure with Invalid Id")
  public void search_For_Adventure_with_InvalidID() {
	  given().spec(reqSep)
	  .log().ifValidationFails()
	  .queryParam("adventure", "890890789")
	  .when().get(Endpoints.GETADVENTUREDETAILSAPI)
	  .then().statusCode(400)
	  .body("message", Matchers.equalToIgnoringCase("Adventure details not found for 890890789!"));
	  
  }
}
