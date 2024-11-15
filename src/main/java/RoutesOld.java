import java.util.Iterator;
import java.util.LinkedList;

public class RoutesOld implements Iterable<RouteOld> {
    LinkedList<RouteOld> routeOlds = new LinkedList<>();

    public void add(RouteOld routeOld) {
        routeOlds.add(routeOld);
    }

    @Override
    public Iterator<RouteOld> iterator() {
        return routeOlds.iterator();
    }
}
