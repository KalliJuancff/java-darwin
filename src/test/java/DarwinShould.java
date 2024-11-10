import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @ParameterizedTest
    @CsvSource({
            "'Darwin', 'Hi, Darwin!'",
            "'Dio', 'Hi, Dio!'",
            "'Gud', 'Hi, Gud!'"
    })
    public void retrieve_one_string_parameter(String name, String expectedBody) {
        RestAssured.baseURI = "http://localhost:8080";
        String existentEndpoint = "/greet?name=" + name;

        Main.main(new String[]{});

        given()
                .when()
                .get(existentEndpoint)
                .then()
                .statusCode(200)
                .body(equalTo(expectedBody));
    }

    @Test
    public void retrieve_more_than_one_string_parameter() {
        RestAssured.baseURI = "http://localhost:8080";
        String existentEndpoint = "/greet?name=Darwin&name2=Dio";

        Main.main(new String[]{});

        given()
                .when()
                .get(existentEndpoint)
                .then()
                .statusCode(200)
                .body(equalTo("Hi, Darwin and Dio!"));
    }

    @Test
    public void retrieve_more_than_one_string_parameter_V2() {
        RestAssured.baseURI = "http://localhost:8080";
        String existentEndpoint = "/greet?name=Darwin&name2=Dio&name3=Gud";

        Main.main(new String[]{});

        given()
                .when()
                .get(existentEndpoint)
                .then()
                .statusCode(200)
                .body(equalTo("Hi, Darwin and Dio and Gud!"));
    }
}
