package bookspractice;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

public class BaseClass {

    public RequestSpecification requestSpec;
    public ResponseSpecification responseSpec;

    @BeforeClass
    public void generalSpec(){
        requestSpec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com")
                .build();
        responseSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
    }
}
