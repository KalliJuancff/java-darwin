import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class RouteManager {
    private final List<String> paths = new ArrayList<>();
    private final Map<HttpMethod, Routes> routes = Map.of(
            HttpMethod.GET, new Routes(),
            HttpMethod.POST, new Routes(),
            HttpMethod.DELETE, new Routes()
    );

    public void addGetRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        paths.add(path);
        routes.get(HttpMethod.GET).add(new Route(path, handler));
    }

    public void addPostRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        paths.add(path);
        routes.get(HttpMethod.POST).add(new Route(path, handler));
    }

    public void addDeleteRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        paths.add(path);
        routes.get(HttpMethod.DELETE).add(new Route(path, handler));
    }

    public HttpResponse responseTo(HttpRequest httpRequest) {
        boolean pathFound = false;
        for (String path : paths) {
            if (httpRequest.hasPathEqualTo(path)) {
                pathFound = true;
            }
        }

        if (pathFound) {
            if (routes.containsKey(httpRequest.method())) {
                for (Route route : routes.get(httpRequest.method())) {
                    if (route.matches(httpRequest)) {
                        HttpResponse httpResponse = HttpResponse.internalServerError();
                        route.handle(httpRequest, httpResponse);
                        return httpResponse;
                    }
                }
                return HttpResponse.methodNotAllowed();
            }
        }

        return HttpResponse.notFound();
    }
}
