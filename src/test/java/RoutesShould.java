import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoutesShould {
    @Test
    public void consider_no_match_when_none_route_is_not_registered() {
        assertThat(new Routes().matches(new Path("/any-route"))).isFalse();
    }
}
