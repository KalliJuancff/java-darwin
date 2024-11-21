import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoutesShould {
    @Test
    public void consider_no_match_when_none_endpoint_is_registered() {
        String ANY_HTTP_METHOD = "GET";
        String ANY_PATH = "/any-path";
        assertThat(new Routes().matches(createHttpRequest(ANY_HTTP_METHOD, ANY_PATH))).isFalse();
    }

    @Test
    public void consider_match_when_endpoint_is_registered() {
        final String HTTP_METHOD = "POST";
        final String PATH = "/path";
        Routes sut = new Routes();
        sut.add(createRoute(HTTP_METHOD, PATH));

        boolean result = sut.matches(createHttpRequest(HTTP_METHOD, PATH));

        assertThat(result).isTrue();
    }

    private static HttpRequest createHttpRequest(String httpMethod, String path) {
        return HttpRequest.from(HttpMethod.from(httpMethod) + " " + path + " " + "HTTP/1.1");
    }

    private static Route createRoute(String httpMethod, String path) {
        return new Route(new EndPoint(HttpMethod.from(httpMethod), new Path(path)), (req, res) -> { });
    }
}
