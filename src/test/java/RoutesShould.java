import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoutesShould {
    @Test
    public void consider_no_match_when_path_is_not_registered() {
        assertThat(new Routes().matches(new Path("/any-route"))).isFalse();
    }

    @Test
    public void consider_match_when_path_is_registered() {
        final String PATH = "/any-route";
        final HttpMethod ANY_HTTP_METHOD = HttpMethod.GET;
        Route anyRoute = new Route(new EndPoint(ANY_HTTP_METHOD, new Path(PATH)), (req, res) -> { });
        Routes sut = new Routes();
        sut.add(anyRoute);

        assertThat(sut.matches(new Path(PATH))).isTrue();
    }
}
