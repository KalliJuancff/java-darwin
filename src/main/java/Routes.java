import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Routes implements Iterable<Route> {
    private final List<Route> routes = new ArrayList<>();

    public void add(Route route) {
        if (route == null) {
            throw new IllegalArgumentException("Route cannot be null");
        }

        routes.add(route);
    }

    public List<Path> allPaths() {
        return routes.stream()
                .map(Route::path)
                .toList();
    }

    @Override
    public Iterator<Route> iterator() {
        return routes.iterator();
    }
}
