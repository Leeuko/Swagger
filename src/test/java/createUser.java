import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import org.json.simple.JSONObject;
import static org.hamcrest.core.IsEqual.equalTo;

public class createUser {

 private static RequestSpecification requestSpecification;
 private static ResponseSpecification responseSpecification;
 public static final String pola = "/pola";
 public static final String nina = "/nina";

 @BeforeClass
    public static void createRequestAndResponse() {

     //create request specification
     requestSpecification = new RequestSpecBuilder()
             //setting Base URI by default projectwide
             .setBaseUri("https://petstore.swagger.io/v2/user")
             //setting Base Path by default projectwide
             //.setBasePath("/createWithArray")
             //set accept to json by default for rest assured
             .setAccept("application/json")
             //add authorization for swagger
             .addHeader("api_key", "2411")
             .build();

     //create request specification for create
     responseSpecification = new ResponseSpecBuilder()
             //verify that request has succeeded and a new resource has been created
             .expectStatusCode(201)
             //set content type to json by default for rest assured
             .expectContentType("application/json")
             .build();

 }

    @Test
    public void CreateUser1()
    {
      given()
              //use request specification
              .spec(requestSpecification)
              //setting Base Path in the test, because get test is in the same class
              .basePath("/createWithArray")
              // use curl data from swagger to set data for new user
              .body("[  {    \"id\": 1,    \"username\": \"pola\",    " +
                      "\"firstName\": \"Pola\",    \"lastName\": \"Black\",    \"email\": \"blackpola@gmail.com\",    " +
                      "\"password\": \"Pola123\",    \"phone\": \"454545\",    \"userStatus\": available  }]")
              //perform post
              .when().post()
              //use response specification
              .then().spec(responseSpecification);
    }

    @Test
    public  void CreateUser2(){
        // user JSON Objects to add data for new user
        JSONObject user = new JSONObject();
        user.put("id", "2");
        user.put("username", "nina");
        user.put("firstName", "Nina");
        user.put("lastName", "Black");
        user.put("email", "blacknina@gmail.com");
        user.put("password", "Nina123");
        user.put("phone", "45454554");
        user.put("userStatus", "online");

        given()
                //use request specification
                .spec(requestSpecification)
                //setting Base Path in the test, because get test is in the same class
                .basePath("/createWithArray")
                //convert JSON data to String
                .body(user.toJSONString())
                //perform post
                .when().post()
                //use response specification
                .then().spec(responseSpecification);
    }

    @Test
    public void FindUser1() {

        given()
                //use request specification
                .spec(requestSpecification)
                //use specific link for get
                .get(createUser.pola)
                .then()
                //request has succeeded
                .statusCode(200)
                //Verify username
                .body("username", equalTo("poli"))
                .log().all();

    }

    @Test
    public void FindUser2() {

     String username =
        given()
                //use request specification
                .spec(requestSpecification)
                //use specific link for get
                .get(createUser.nina)
                //Verify username
                .then().extract().path("username");

        Assert.assertEquals(username, "nina");


    }
}