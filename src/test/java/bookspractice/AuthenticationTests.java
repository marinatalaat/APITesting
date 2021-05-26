package bookspractice;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AuthenticationTests extends BaseClass{

    public static String Token;


    @Test
    public void loginWithValidCredentials(){
        Response response = given().log().all().spec(requestSpec).contentType(ContentType.JSON).pathParam("auth" , "auth")
                .body("{\n" +
                        "   \t\t \"username\" : \"admin\",\n" +
                        "   \t\t \"password\" : \"password123\"\n" +
                        "\t\t}")
                .when().post("/{auth}")
                .then().log().body().spec(responseSpec).statusCode(200).extract().response();
        JsonPath path = response.jsonPath();
        Token = path.get("token");
    }



}
