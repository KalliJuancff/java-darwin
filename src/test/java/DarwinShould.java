import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DarwinShould {
    @BeforeEach
    public void setUp() {
         RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void responds_to_a_GET_method_with_a_HTTP_status_code_of_404_and_a_Not_Found_message_if_endpoint_does_not_exist() {
        String nonExistentPath = "/nonexistent-path";

        runApplication();

        given()
                .when()
                .get(nonExistentPath)
                .then()
                .statusCode(404)
                .body(equalTo("Not Found"));
    }

    @Test
    public void responds_to_a_GET_method_with_a_HTTP_status_code_of_200_and_a_OK_message_if_endpoint_exists() {
        String existingPath = "/hello";

        runApplication();

        given()
                .when()
                .get(existingPath)
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
    public void fetches_a_single_string_parameter(String name, String expectedBody) {
        String path = "/greet?name=" + name;

        runApplication();

        given()
                .when()
                .get(path)
                .then()
                .statusCode(200)
                .body(equalTo(expectedBody));
    }

    @ParameterizedTest
    @CsvSource({
            "'?name=Darwin&name2=Dio', 'Darwin and Dio'",
            "'?name=Darwin&name2=Dio&name3=Gud', 'Darwin and Dio and Gud'",
            "'?name=Darwin&name2=Dio&name3=Diu&name4=Gud&name5=Poe', 'Darwin and Dio and Diu and Gud and Poe'"
    })
    public void fetches_more_than_one_string_parameter(String queryString, String expectedNames) {
        String path = "/greet" + queryString;
        String expectedBody = "Hi, " + expectedNames + "!";

        runApplication();

        given()
                .when()
                .get(path)
                .then()
                .statusCode(200)
                .body(equalTo(expectedBody));
    }


    @Test
    public void responds_to_a_POST_method_with_HTTP_status_code_of_201_and_a_Created_message() {
        String existingPath = "/hello";

        runApplication();

        given()
                .when()
                .post(existingPath)
                .then()
                .statusCode(201)
                .body(equalTo("Created"));
    }


    private static void runApplication() {
        new Application().run();
    }
}
