import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DarwinShould {
    @Test
    public void returns_a_HTTP_status_code_of_404_if_endpoint_does_not_exist() {
        RestAssured.baseURI = "http://localhost:8080";
        String nonExistentEndpoint = "/nonexistent-endpoint";

        Main.main(new String[]{});

        given()
                .when()
                .get(nonExistentEndpoint)
                .then()
                .statusCode(404);
    }

    @Test
    public void returns_a_HTTP_status_code_of_200_if_endpoint_exists() {
        RestAssured.baseURI = "http://localhost:8080";
        String nonExistentEndpoint = "/hello";

        Main.main(new String[]{});

        given()
                .when()
                .get(nonExistentEndpoint)
                .then()
                .statusCode(200);
    }
}
