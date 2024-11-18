import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoutingCoordinatorShould {
    @Test
    public void return_404_when_no_endpoint_matches() {
        var httpRequest = HttpRequest.from("GET /unknown HTTP/1.1");
        RoutingCoordinator sut = new RoutingCoordinator();

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isEqualTo(404);
    }

    @Test
    public void does_not_return_404_when_endpoint_matches_and_http_method_is_GET() {
        var httpRequest = HttpRequest.from("GET /known HTTP/1.1");
        RoutingCoordinator sut = new RoutingCoordinator();
        sut.addGetRoute("/known", (req, res) -> res.convertTo(HttpResponse.ok()));

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isNotEqualTo(404);
    }

    @Test
    public void does_not_return_404_when_endpoint_matches_and_http_method_is_POST() {
        var httpRequest = HttpRequest.from("POST /known HTTP/1.1");
        RoutingCoordinator sut = new RoutingCoordinator();
        sut.addPostRoute("/known", (req, res) -> res.convertTo(HttpResponse.ok()));

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isNotEqualTo(404);
    }
}
