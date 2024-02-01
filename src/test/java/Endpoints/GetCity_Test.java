package Endpoints;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Constants.Endpoints;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

public class GetCity_Test {
	RequestSpecBuilder builder;
	RequestSpecification reqSep;
	ResponseSpecBuilder resBuilder;
	ResponseSpecification resSep;
		
	
  @BeforeClass
  public void setup() throws IOException {
	  builder = new RequestSpecBuilder();
	  builder.setBaseUri(Endpoints.BASE_URL);
	  builder.setBasePath(Endpoints.BASE_PATH);
	  builder.setContentType(ContentType.JSON);
	  reqSep=builder.build();
	  resBuilder = new ResponseSpecBuilder();
	  resBuilder.expectStatusCode(200);
	  resBuilder.expectResponseTime(Matchers.lessThanOrEqualTo(3L), TimeUnit.SECONDS);
	  resSep = resBuilder.build();
	  
  }
  
  @Test(description = "Verify All City Listed")
  public void getAllCity() {
	  given().spec(reqSep)
	  .log().ifValidationFails(LogDetail.ALL)
	  .when().get(Endpoints.GETCITYAPI)
	  .then()
	  .spec(resSep);  
  }
  
  @Test(description = "Search for a Specify City")
  public void getAllCity1() {
	  given().spec(reqSep)
	  .queryParams("q", "bengaluru")
	  .log().ifValidationFails(LogDetail.ALL)
	  .when().get(Endpoints.GETCITYAPI)
	  .then()
	  .spec(resSep)
	  .assertThat().body("city", Matchers.hasItem("Bengaluru"));
  }
  
  @Test(description = "Search for a Invalid City")
  public void getAllCity3() {
	  given().spec(reqSep)
	  .queryParams("q", "Kfssdfds")
	  .log().ifValidationFails(LogDetail.ALL)
	  .when().get(Endpoints.GETCITYAPI)
	  .then()
	  .spec(resSep)
	  ;
  }
  
  @Test(description = "search query length <3")
  public void getAllCity4() {
	  given().spec(reqSep)
	  .queryParams("q", "Kf")
	  .log().ifValidationFails(LogDetail.ALL)
	  .when().get(Endpoints.GETCITYAPI)
	  .then()
	  .statusCode(404)
	  .assertThat().body("message", Matchers.containsString("City Query length should be atleast 3! Currently it is only 2"))
	  ;
  } 

}
