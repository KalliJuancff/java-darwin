import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class RouteManager {
    private final List<String> paths = new ArrayList<>();
    private final Map<HttpMethod, RoutesOld> routesOld = Map.of(
            HttpMethod.GET, new RoutesOld(),
            HttpMethod.POST, new RoutesOld(),
            HttpMethod.DELETE, new RoutesOld()
    );
    private final Routes routes = new Routes();

    public void addGetRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        paths.add(path);
        routesOld.get(HttpMethod.GET).add(new RouteOld(path, handler));

        var endpoint = new EndPoint(HttpMethod.GET, path);
        var route = new Route(endpoint, handler);
        routes.add(route);
    }

    public void addPostRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        paths.add(path);
        routesOld.get(HttpMethod.POST).add(new RouteOld(path, handler));

        var endpoint = new EndPoint(HttpMethod.POST, path);
        var route = new Route(endpoint, handler);
        routes.add(route);
    }

    public void addDeleteRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        paths.add(path);
        routesOld.get(HttpMethod.DELETE).add(new RouteOld(path, handler));

        var endpoint = new EndPoint(HttpMethod.POST, path);
        var route = new Route(endpoint, handler);
        routes.add(route);
    }

    public HttpResponse responseTo(HttpRequest httpRequest) {
        if (!httpRequest.isPathContainedIn(routes.allPaths())) {
            return HttpResponse.notFound();
        }

        return responseToOld(httpRequest);
    }

    public HttpResponse responseToOld(HttpRequest httpRequest) {
        if (routesOld.containsKey(httpRequest.method())) {
            for (RouteOld routeOld : routesOld.get(httpRequest.method())) {
                if (routeOld.matches(httpRequest)) {
                    HttpResponse httpResponse = HttpResponse.internalServerError();
                    routeOld.handle(httpRequest, httpResponse);
                    return httpResponse;
                }
            }
            return HttpResponse.methodNotAllowed();
        }

        return HttpResponse.internalServerError();
    }
}
