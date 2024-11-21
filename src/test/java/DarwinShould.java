import io.restassured.RestAssured;
import org.hamcrest.core.IsNot;
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
    public static final String HELLO_PATH = "/hello";
    public static final String GREET_PATH = "/greet";

    private Application app;

    @BeforeEach
    public void setUp() {
        app = new Application();
    }


    @Test
    public void respond_with_a_HTTP_status_code_of_404_and_a_Not_Found_if_endpoint_does_not_exist() {
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


    @ParameterizedTest
    @CsvSource({
            "Darwin",
            "Dio",
            "Gud"
    })
    public void fetch_single_string_parameter(String name) {
        String uri = "/greet" + "?name=" + name;
        app.get("/greet", (req, res) -> {
            String name = extractNamesFrom(req);
            res.convertTo(HttpResponse.ok("Hi, " + name + "!"));
        });
        String expectedBody = "Hi, " + name + "!";

        listen();

        given()
                .when()
                .get(uri)
                .then()
                .body(equalTo(expectedBody));
    }

    @ParameterizedTest
    @CsvSource({
            "'?name=Darwin&name2=Dio', 'Darwin and Dio'",
            "'?name=Darwin&name2=Dio&name3=Gud', 'Darwin and Dio and Gud'",
            "'?name=Darwin&name2=Dio&name3=Diu&name4=Gud&name5=Poe', 'Darwin and Dio and Diu and Gud and Poe'"
    })
    public void fetch_more_than_one_string_parameter(String queryString, String expectedNames) {
        String uri = "/greet" + queryString;
        String expectedBody = "Hi, " + expectedNames + "!";
        app.get("/greet", (req, res) -> {
            String names = extractNamesFrom(req);
            res.convertTo(HttpResponse.ok("Hi, " + names + "!"));
        });

        listen();

        given()
                .when()
                .get(uri)
                .then()
                .body(equalTo(expectedBody));
    }


    @ParameterizedTest
    @CsvSource({
            "'GET', " + HELLO_PATH,
            "'DELETE', " + HELLO_PATH,
            "'POST', " + GREET_PATH,
            "'DELETE', " + GREET_PATH
    })
    public void respond_with_a_HTTP_status_code_of_405_and_a_Method_not_allowed_if_path_exists_but_HTTP_method_is_not_configured(
            String method, String path) {
        app.post(HELLO_PATH, (req, res) -> {
            res.convertTo(HttpResponse.created());
        });
        app.get(GREET_PATH, (req, res) -> {
            res.convertTo(HttpResponse.ok("Hello, world!"));
        });

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
    public void respond_with_a_HTTP_status_code_of_500_and_a_Internal_Server_Error_if_user_callback_throws_an_exception() {
        String ANY_ERROR_MESSAGE = "Any error message";
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


    @Test
    public void fetch_a_single_route_parameter() {
        app.get("/users/{userId}", (req, res) -> {
            res.convertTo(HttpResponse.ok(req.pathParameters()));
        });
        String expectedBody = "{ \"userId\": \"7\" }";

        listen();

        given()
                .when()
                .get("/users/7")
                .then()
                .body(equalTo(expectedBody));
    }

    @Test
    public void fetch_more_than_one_route_parameter() {
        app.get("/users/{userId}/books/{bookId}", (req, res) -> {
            res.convertTo(HttpResponse.ok(req.pathParameters()));
        });
        String expectedBody = "{ \"userId\": \"4\", \"bookId\": \"3\" }";

        listen();

        given()
                .when()
                .get("/users/4/books/3")
                .then()
                .body(equalTo(expectedBody));
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

    private static String extractNamesFrom(HttpRequest httpRequest) {
        var names = new StringBuilder();
        for (QueryParameter queryParameter : httpRequest.queryParameters()) {
            if (!names.toString().isEmpty()) {
                names.append(" and ");
            }
            names.append(queryParameter.value());
        }
        return names.toString();
    }
}
