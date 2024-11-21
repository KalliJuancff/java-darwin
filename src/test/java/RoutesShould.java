import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoutesShould {
    @Test
    public void consider_no_match_when_none_route_is_not_registered() {
        assertThat(new Routes().matches(new Path("/any-route"))).isFalse();
    }

    @Test
    public void consider_match_when_verb_and_path_are_registered() {
        String path = "/any-route";
        Routes sut = new Routes();
        sut.add(new Route(new EndPoint(HttpMethod.GET, new Path(path)), (req, res) -> {}));

        assertThat(sut.matches(new Path(path))).isTrue();
    }
}
