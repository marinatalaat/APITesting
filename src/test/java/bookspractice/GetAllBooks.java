package bookspractice;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetAllBooks extends BaseClass{

    @Test
    public void getAllBooksTest(){
        given().log().all().spec(requestSpec).pathParam("booking" , "booking")
                .when().get("/{booking}")
                .then().log().body().statusCode(200);
    }

}
