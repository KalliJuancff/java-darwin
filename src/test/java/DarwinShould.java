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
        String nonExistentPath = "/nonexistent-path";

        Main.main(new String[]{});

        given()
                .when()
                .get(nonExistentPath)
                .then()
                .statusCode(404)
                .body(equalTo("Not Found"));
    }

    @Test
    public void returns_a_HTTP_status_code_of_200_and_a_OK_message_if_endpoint_exists() {
        RestAssured.baseURI = "http://localhost:8080";
        String existingPath = "/hello";

        Main.main(new String[]{});

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
    public void retrieve_a_single_string_parameter(String name, String expectedBody) {
        RestAssured.baseURI = "http://localhost:8080";
        String path = "/greet?name=" + name;

        Main.main(new String[]{});

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
    public void retrieve_more_than_one_string_parameter(String queryString, String expectedNames) {
        RestAssured.baseURI = "http://localhost:8080";
        String path = "/greet" + queryString;

        Main.main(new String[]{});

        given()
                .when()
                .get(path)
                .then()
                .statusCode(200)
                .body(equalTo("Hi, " + expectedNames + "!"));
    }
}
