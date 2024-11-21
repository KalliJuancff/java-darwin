import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoutingCoordinatorShould {
    @Test
    public void return_404_when_no_endpoint_matches() {
        var sut = new RoutingCoordinator();
        var httpRequest = HttpRequest.from("GET /unknown HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isEqualTo(404);
    }

    @Test
    public void does_not_return_404_when_an_endpoint_matches_because_get_route_was_added() {
        var sut = new RoutingCoordinator();
        sut.addGetRoute("/known", (req, res) -> res.convertTo(HttpResponse.ok()));
        var httpRequest = HttpRequest.from("GET /known HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isNotEqualTo(404);
    }

    @Test
    public void does_not_return_404_when_an_endpoint_matches_because_post_route_was_added() {
        var sut = new RoutingCoordinator();
        sut.addPostRoute("/known", (req, res) -> res.convertTo(HttpResponse.ok()));
        var httpRequest = HttpRequest.from("POST /known HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isNotEqualTo(404);
    }

    @Test
    public void does_not_return_404_when_an_endpoint_matches_because_delete_route_was_added() {
        var sut = new RoutingCoordinator();
        sut.addDeleteRoute("/known", (req, res) -> res.convertTo(HttpResponse.ok()));
        var httpRequest = HttpRequest.from("DELETE /known HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isNotEqualTo(404);
    }


    @Test
    public void return_500_when_user_callback_throws_an_exception() {
        final String ANY_EXCEPTION_MESSAGE = "Something went wrong";
        var sut = new RoutingCoordinator();
        sut.addGetRoute("/", (req, res) -> {
            throw new RuntimeException(ANY_EXCEPTION_MESSAGE);
        });
        var httpRequest = HttpRequest.from("GET / HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isEqualTo(500);
        assertThat(httpResponse.body()).isEqualTo("Error: '" + ANY_EXCEPTION_MESSAGE + "'");
    }


    @Test
    public void return_405_when_an_endpoint_matches_but_not_GET_http_method() {
        var sut = new RoutingCoordinator();
        sut.addPostRoute("/", (req, res) -> res.convertTo(HttpResponse.ok()));
        sut.addDeleteRoute("/", (req, res) -> res.convertTo(HttpResponse.ok()));
        var httpRequest = HttpRequest.from("GET / HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isEqualTo(405);
    }

    @Test
    public void return_405_when_an_endpoint_matches_but_not_POST_http_method() {
        var sut = new RoutingCoordinator();
        sut.addGetRoute("/", (req, res) -> res.convertTo(HttpResponse.ok()));
        sut.addDeleteRoute("/", (req, res) -> res.convertTo(HttpResponse.ok()));
        var httpRequest = HttpRequest.from("POST / HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isEqualTo(405);
    }

    @Test
    public void return_405_when_an_endpoint_matches_but_not_DELETE_http_method() {
        var sut = new RoutingCoordinator();
        sut.addGetRoute("/", (req, res) -> res.convertTo(HttpResponse.ok()));
        sut.addPostRoute("/", (req, res) -> res.convertTo(HttpResponse.ok()));
        var httpRequest = HttpRequest.from("DELETE / HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isEqualTo(405);
    }


    @Test
    public void fetch_single_path_parameter() {
        var sut = new RoutingCoordinator();
        sut.addGetRoute("/products/{productId}", (req, res) ->
                res.convertTo(HttpResponse.ok("{ \"productId\": \"" + req.pathParameter("productId") + "\" }")));
        var httpRequest = HttpRequest.from("GET /products/34 HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isEqualTo(200);
        assertThat(httpResponse.body()).isEqualTo("{ \"productId\": \"34\" }");
    }
}
