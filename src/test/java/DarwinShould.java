import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DarwinShould {
    @Test
    public void returns_a_HTTP_status_code_of_404_and_a_Not_Found_message_if_endpoint_does_not_exist() {
        RestAssured.baseURI = "http://localhost:8080";
        String nonExistentEndpoint = "/nonexistent-endpoint";

        Main.main(new String[]{});

        given()
                .when()
                .get(nonExistentEndpoint)
                .then()
                .statusCode(404)
                .body(equalTo("Not Found"));
    }


    @Test
    public void returns_a_HTTP_status_code_of_200_if_endpoint_exists() {
        RestAssured.baseURI = "http://localhost:8080";
        String existentEndpoint = "/hello";

        Main.main(new String[]{});

        given()
                .when()
                .get(existentEndpoint)
                .then()
                .statusCode(200)
                .body(equalTo("OK"));
    }

    @Test
    public void retrieve_one_string_parameter() {
        RestAssured.baseURI = "http://localhost:8080";
        String existentEndpoint = "/greet?name=Darwin";

        Main.main(new String[]{});

        given()
                .when()
                .get(existentEndpoint)
                .then()
                .statusCode(200)
                .body(equalTo("Hi, Darwin!"));
    }

    @Test
    public void retrieve_one_string_parameter_V2() {
        RestAssured.baseURI = "http://localhost:8080";
        String existentEndpoint = "/greet?name=Gott";

        Main.main(new String[]{});

        given()
                .when()
                .get(existentEndpoint)
                .then()
                .statusCode(200)
                .body(equalTo("Hi, Gott!"));
    }
}
