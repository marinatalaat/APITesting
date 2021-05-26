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

import java.lang.annotation.Target;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class WeatherApiTests {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    String path = "data/2.5/weather";

    @BeforeClass
    public void generalSpec(){
        requestSpec = new RequestSpecBuilder().setBaseUri("https://api.openweathermap.org/")
                .build();
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    }

    @Test
    public void weatherOfCountryByName(){
        Response response = given().log().all().spec(requestSpec).pathParam("Weather", path)
                .queryParam("q" , "cairo").queryParam("appid" , "5591cb3fcc2d6c4445455fd59b39b5be")
                .when().get("/{Weather}")
                .then().log().body().spec(responseSpec).extract().response();

        JsonPath json = response.jsonPath();

        Assert.assertEquals(json.get("name") , "Cairo");
    }


    @Test
    public void weatherOfCountryByID(){
        Response response = given().log().all().spec(requestSpec).pathParam("Weather", path)
                .queryParam("id" , "2288873").queryParam("appid" , "5591cb3fcc2d6c4445455fd59b39b5be")
                .when().get("/{Weather}")
                .then().log().body().spec(responseSpec).extract().response();

        JsonPath json = response.jsonPath();

        Assert.assertEquals(json.get("name") , "France");
    }


    @Test
    public void weatherOfCountryByGeographicCoordinates(){
        given().log().all().spec(requestSpec).pathParam("Weather", path)
                .queryParam("lat" , "51.5").queryParam("lon" , "10.5").queryParam("appid" , "5591cb3fcc2d6c4445455fd59b39b5be")
                .when().get("/{Weather}")
                .then().log().body().spec(responseSpec).body("name" , equalTo("Germany"));

    }

    @Test
    public void weatherOfCountryByZipCode(){
        given().log().all().spec(requestSpec).pathParam("Weather", path)
                .queryParam("zip" , "94040").queryParam("appid" , "5591cb3fcc2d6c4445455fd59b39b5be")
                .queryParam("units" , "imperial")
                .when().get("/{Weather}")
                .then().log().body().spec(responseSpec).body("name" , equalTo("Mountain View"));
    }
}
