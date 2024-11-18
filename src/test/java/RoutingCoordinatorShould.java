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
}
