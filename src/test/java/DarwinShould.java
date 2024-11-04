import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DarwinShould {
    @Test
    public void returns_a_HTTP_status_code_of_404() {
        RestAssured.baseURI = "http://localhost:8080";
        String nonExistentEndpoint = "/nonexistent-endpoint";

        Main.main(new String[]{});

        given()
                .when()
                .get(nonExistentEndpoint)
                .then()
                .statusCode(404);
    }
}
