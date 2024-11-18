import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoutingCoordinatorShould {
    @Test
    public void return_404_when_no_endpoint_matches() {
        RoutingCoordinator sut = new RoutingCoordinator();
        var httpRequest = HttpRequest.from("GET /unknown HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isEqualTo(404);
    }

    @Test
    public void does_not_return_404_when_an_endpoint_matches_v1() {
        RoutingCoordinator sut = new RoutingCoordinator();
        sut.addGetRoute("/known", (req, res) -> res.convertTo(HttpResponse.ok()));
        var httpRequest = HttpRequest.from("GET /known HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isNotEqualTo(404);
    }

    @Test
    public void does_not_return_404_when_an_endpoint_matches_v2() {
        RoutingCoordinator sut = new RoutingCoordinator();
        sut.addPostRoute("/known", (req, res) -> res.convertTo(HttpResponse.ok()));
        var httpRequest = HttpRequest.from("POST /known HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isNotEqualTo(404);
    }

    @Test
    public void does_not_return_404_when_an_endpoint_matches_v3() {
        RoutingCoordinator sut = new RoutingCoordinator();
        sut.addDeleteRoute("/known", (req, res) -> res.convertTo(HttpResponse.ok()));
        var httpRequest = HttpRequest.from("DELETE /known HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isNotEqualTo(404);
    }


    @Test
    public void return_405_when_an_endpoint_matches_but_not_the_http_method_v1() {
        RoutingCoordinator sut = new RoutingCoordinator();
        sut.addGetRoute("/known", (req, res) -> res.convertTo(HttpResponse.ok()));
        var httpRequest = HttpRequest.from("POST /known HTTP/1.1");

        HttpResponse httpResponse = sut.responseTo(httpRequest);

        assertThat(httpResponse.statusCode()).isEqualTo(405);
    }
}
