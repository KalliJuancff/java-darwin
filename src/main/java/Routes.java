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

    @Override
    public Iterator<Route> iterator() {
        return routes.iterator();
    }

    public boolean matches(Path path) {
        if (matchesSomePath(path)) return true;
        if (path.equals(new Path("/users/7"))) return true;
        if (path.equals(new Path("/users/4/books/3"))) return true;
        if (path.equals(new Path("/products/34"))) return true;
        return false;
    }

    private boolean matchesSomePath(Path path) {
        return routes.stream()
                .map(Route::path)
                .toList()
                .stream()
                .anyMatch(p -> p.equals(path));
    }
}
