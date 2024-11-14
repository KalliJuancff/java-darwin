import java.util.Iterator;
import java.util.LinkedList;

public class Routes implements Iterable<Route> {
    LinkedList<Route> routes = new LinkedList<>();

    public void add(Route route) {
        routes.add(route);
    }

    @Override
    public Iterator<Route> iterator() {
        return routes.iterator();
    }
}
