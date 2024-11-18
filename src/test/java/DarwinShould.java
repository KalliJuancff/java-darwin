import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.ServerSocket;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;

public class DarwinShould {
    private Application app;

    @BeforeEach
    public void setUp() {
        createAndInitializeApplication();
    }

    private void createAndInitializeApplication() {
        app = new Application();

        app.get("/hello", (req, res) -> {
            res.convertTo(HttpResponse.ok());
        });
        app.post("/hello", (req, res) -> {
            res.convertTo(HttpResponse.created());
        });
        app.get("/greet", (req, res) -> {
            StringBuilder names = extractNamesFrom(req);
            res.convertTo(HttpResponse.ok("Hi, " + names + "!"));
        });
    }

    private static StringBuilder extractNamesFrom(HttpRequest httpRequest) {
        StringBuilder names = new StringBuilder();
        for (QueryParameter queryParameter : httpRequest.queryParameters()) {
            if (!names.toString().isEmpty()) {
                names.append(" and ");
            }
            names.append(queryParameter.value());
        }
        return names;
    }


    @Test
    public void responds_with_a_HTTP_status_code_of_404_and_a_Not_Found_if_endpoint_does_not_exist() {
        String nonExistentPath = "/nonexistent-path";

        listen();

        given()
                .when()
                .get(nonExistentPath)
                .then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .body(emptyOrNullString());
    }

    @Test
    public void responds_with_a_HTTP_status_code_of_200_and_a_OK_if_endpoint_exists() {
        String existingPath = "/hello";

        listen();

        given()
                .when()
                .get(existingPath)
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .body(emptyOrNullString());
    }


    @ParameterizedTest
    @CsvSource({
            "'Darwin', 'Hi, Darwin!'",
            "'Dio', 'Hi, Dio!'",
            "'Gud', 'Hi, Gud!'"
    })
    public void fetches_a_single_string_parameter(String name, String expectedBody) {
        String uri = "/greet?name=" + name;

        listen();

        given()
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .body(equalTo(expectedBody));
    }

    @ParameterizedTest
    @CsvSource({
            "'?name=Darwin&name2=Dio', 'Darwin and Dio'",
            "'?name=Darwin&name2=Dio&name3=Gud', 'Darwin and Dio and Gud'",
            "'?name=Darwin&name2=Dio&name3=Diu&name4=Gud&name5=Poe', 'Darwin and Dio and Diu and Gud and Poe'"
    })
    public void fetches_more_than_one_string_parameter(String queryString, String expectedNames) {
        String uri = "/greet" + queryString;
        String expectedBody = "Hi, " + expectedNames + "!";

        listen();

        given()
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .body(equalTo(expectedBody));
    }


    @Test
    public void responds_to_a_POST_method_with_a_HTTP_status_code_of_201_and_a_Created() {
        String existingPath = "/hello";

        listen();

        given()
                .when()
                .post(existingPath)
                .then()
                .statusCode(201)
                .statusLine("HTTP/1.1 201 Created")
                .body(emptyOrNullString());
    }


    @ParameterizedTest
    @CsvSource({
            "'DELETE', '/hello'",
            "'POST', '/greet'",
            "'DELETE', '/greet'"
    })
    public void responds_with_a_HTTP_status_code_of_405_and_a_Method_not_allowed_if_path_exists_but_HTTP_method_is_not_configured(
            String method, String path) {
        listen();

        given()
                .when()
                .request(method, path)
                .then()
                .statusCode(405)
                .statusLine("HTTP/1.1 405 Method Not Allowed")
                .body(emptyOrNullString());
    }

    @Test
    public void does_not_respond_with_a_HTTP_status_code_of_405_and_a_Method_not_allowed_message_if_path_exists_and_HTTP_method_is_configured() {
        String path = "/greet";
        app.delete(path, (req, res) -> {
            res.convertTo(HttpResponse.ok());
        });

        listen();

        given()
                .when()
                .delete(path)
                .then()
                .statusCode(200));
    }


    @Test
    public void respond_with_a_HTTP_status_code_of_500_and_a_Internal_Server_Error_if_user_callback_throws_an_exception() {
        final String ANY_ERROR_MESSAGE = "Any error message";

        String path = "/boom";
        app.get(path, (req, res) -> {
            throw new RuntimeException(ANY_ERROR_MESSAGE);
        });

        listen();

        given()
                .when()
                .get(path)
                .then()
                .statusCode(500)
                .statusLine("HTTP/1.1 500 Internal Server Error")
                .body(equalTo("Error: '" + ANY_ERROR_MESSAGE + "'"));
    }


    private void listen() {
        int availablePort = findAvailableTcpPort();
        RestAssured.baseURI = "http://localhost:" + availablePort;

        app.listen(availablePort);
    }

    private int findAvailableTcpPort() {
        // https://www.baeldung.com/java-free-port
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException ex) {
            throw new RuntimeException("Could not find available port", ex);
        }
    }
}
