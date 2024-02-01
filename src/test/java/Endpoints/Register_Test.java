package Endpoints;

import java.io.IOException;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import org.testng.annotations.AfterClass;
import Constants.Endpoints;
import Utility.ReadJSON;
import Utility.ReadUpdateJSON;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Register_Test {
	
	
	RequestSpecBuilder builder;
	RequestSpecification reqSep;
	String payload;
	String email;
	
  @BeforeClass
  public void setup() throws IOException {
	  builder = new RequestSpecBuilder();
	  builder.setBaseUri(Endpoints.BASE_URL);
	  builder.setBasePath(Endpoints.BASE_PATH);
	  builder.setContentType(ContentType.JSON);
	  reqSep=builder.build();
  }
  @AfterClass
  public void tearDown() {
	  ReadUpdateJSON.addNewValue("Email", email);
	  ReadUpdateJSON.addNewValue("password", "s@gmail.com");
	  
  }
  
  @Test(description = "create New User")
  public void createUser() throws Exception {
	  //Preparing Payload for this test 
	  email=UUID.randomUUID().toString()+"@gmail.com";
	  ReadJSON.updatePayload("email", email, "RegisterUser_Payload.json");
	  payload=ReadJSON.convertJSON("RegisterUser_Payload.json");
	  
	  given().spec(reqSep).body(payload).log().all()
	  .when().post(Endpoints.REGISTERAPI)
	  .then().log().all()
	  .statusCode(201)
	  .assertThat().body("success",Matchers.is(true));
	  
	 }
  
  @Test(description = "create New User With Existing Email",dependsOnMethods ="createUser")
  public void createUserWithExistingEmail() {
	  given().spec(reqSep).body(payload).log().all()
	  .when().post(Endpoints.REGISTERAPI)
	  .then().log().all()
	  .statusCode(400)
	  .assertThat().body("success",Matchers.is(false))
	  .body("message", Matchers.containsString("Email already exists"));
	  
	 }
  
  @Test(description = "create New User With Password less that 6 charactr")
  public void createUserPwd() {
	  String email = UUID.randomUUID().toString()+"@gmail.com";
	  String pwd ="134";
	  String payloadd="{\r\n"
	  		+ "   \"email\":\""+email+"\",\r\n" 
	  		+ "   \"password\":\""+pwd+"\",\r\n"
	  		+ "   \"confirmpassword\":\""+pwd+"\"\r\n"
	  		+ "}\r\n"
	  		+ "";
	  given().spec(reqSep).body(payloadd).log().all()
	  .when().post(Endpoints.REGISTERAPI)
	  .then().log().all()
	  .statusCode(400)
	  .assertThat().body("message",Matchers.containsString("Password must be atleast 6 in length"));
	 }
  

}
