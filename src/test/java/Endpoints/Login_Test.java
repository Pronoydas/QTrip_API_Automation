package Endpoints;

import java.io.IOException;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import Constants.Endpoints;
import Endpoints_Steps.Login_Steps;
import Utility.ReadJSON;
import Utility.ReadUpdateJSON;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Login_Test {
	
	RequestSpecBuilder builder;
	RequestSpecification reqSep;
	String token;
	String userID;
	
	
  @BeforeClass
  public void setup() throws IOException {
	  builder = new RequestSpecBuilder();
	  builder.setBaseUri(Endpoints.BASE_URL);
	  builder.setBasePath(Endpoints.BASE_PATH);
	  builder.setContentType(ContentType.JSON);
	  reqSep=builder.build();
	  Login_Steps.preparedPayload("Login_Payload.json");
	  
  }
  @AfterClass
  public void tearDown() {
	ReadUpdateJSON.addNewValue("token", token); 
	ReadUpdateJSON.addNewValue("UserId", userID); 
	
	 
	  
  }
  @Test(description = "Login with Valid Credentials")
  public void loginIntoQtrip() throws IOException {
	  Response res=given()
	  .spec(reqSep).log().all()
	  .body(ReadJSON.convertJSON("Login_Payload.json"))
	  .when().post(Endpoints.LOGINAPI)
	  .then().statusCode(201)
	  .assertThat().body("success",Matchers.is(true)).extract().response();
	  token =Login_Steps.getToken(res);
	  userID=Login_Steps.getUserID(res);
  }
  
  @Test(description = "Login with Invalid Credentials")
  public void loginIntoQtrip1() throws IOException {
	  given()
	  .spec(reqSep).log().all()
	  .body(ReadJSON.convertJSON("Invalid_Login_Payload.json"))
	  .when().post(Endpoints.LOGINAPI)
	  .then().statusCode(404)
	  .assertThat().body("success",Matchers.is(false));
	  
  }
  
  
  

}
