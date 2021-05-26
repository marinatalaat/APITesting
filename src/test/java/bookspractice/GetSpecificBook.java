package bookspractice;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetSpecificBook extends BaseClass{


    public int CreatedBookId;

    @Test
    public void getBookById(){
        Response response = given().log().all().spec(requestSpec).pathParam("booking" , "booking").pathParam("ID" , "9")
                .when().get("/{booking}/{ID}")
                .then().log().body().statusCode(200).extract().response();

        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(jsonPath.get("firstname") , "Eric");
        Assert.assertEquals(jsonPath.get("lastname") , "Jackson");
    }


    @Test
    public void getBookByName(){
        Response response = given().log().all().spec(requestSpec).pathParam("booking" , "booking")
                .queryParam("firstname" , "Sally").queryParam("lastname","Ericsson")
                .when().get("/{booking}/")
                .then().log().body().statusCode(200).extract().response();

        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(jsonPath.getInt("[0].bookingid") , 1 , "getBookByName Test Case");
    }




    @Test
    public void createNewBook(){
        Response response = given().log().all().spec(requestSpec)
                .pathParam("booking" , "booking").contentType(ContentType.JSON)
                .body("{\n" +
                        "   \t \t\"firstname\" : \"Jim\",\n" +
                        "   \t \t\"lastname\" : \"Brown\",\n" +
                        "   \t\t \"totalprice\" : 111,\n" +
                        "    \t\t\"depositpaid\" : true,\n" +
                        "    \t\t\"bookingdates\" : {\n" +
                        "    \t\t    \"checkin\" : \"2021-01-01\",\n" +
                        "   \t\t     \"checkout\" : \"2022-01-01\"\n" +
                        "   \t\t },\n" +
                        "   \t\t \"additionalneeds\" : \"Breakfast\"\n" +
                        "\t\t}")
                .when().post("/{booking}/")
                .then().log().body().statusCode(200).extract().response();

        JsonPath jsonPath = response.jsonPath();
        CreatedBookId = jsonPath.getInt("bookingid");

    }



    @Test
    public void updateBook(){
        Response response = given().log().all().spec(requestSpec)
                .pathParam("booking" , "booking").contentType(ContentType.JSON)
                .cookie("token" , AuthenticationTests.Token)
                .body("{\n" +
                        "   \t \t\"firstname\" : \"James\",\n" +
                        "   \t \t\"lastname\" : \"Brown\",\n" +
                        "   \t\t \"totalprice\" : 111,\n" +
                        "    \t\t\"depositpaid\" : true,\n" +
                        "    \t\t\"bookingdates\" : {\n" +
                        "    \t\t    \"checkin\" : \"2021-01-01\",\n" +
                        "   \t\t     \"checkout\" : \"2022-01-01\"\n" +
                        "   \t\t },\n" +
                        "   \t\t \"additionalneeds\" : \"Breakfast\"\n" +
                        "\t\t}")
                .when().put("/{booking}/"+CreatedBookId)
                .then().log().body().extract().response();

        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.get("firstname") , "James");

    }


}
