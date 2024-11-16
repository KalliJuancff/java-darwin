import java.util.function.BiConsumer;

public class RouteManager {
    private final Routes routes = new Routes();

    public void addGetRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        routes.add(createRoute(HttpMethod.GET, path, handler));
    }

    public void addPostRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        routes.add(createRoute(HttpMethod.POST, path, handler));
    }

    public void addDeleteRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        routes.add(createRoute(HttpMethod.DELETE, path, handler));
    }

    private static Route createRoute(HttpMethod method, String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        var endpoint = new EndPoint(method, new Path(path));
        return new Route(endpoint, handler);
    }

    public HttpResponse responseTo(HttpRequest httpRequest) {
        if (!httpRequest.isPathContainedIn(routes)) {
            return HttpResponse.notFound();
        }

        for (Route route : routes) {
            if (route.matches(httpRequest)) {
                HttpResponse httpResponse = HttpResponse.internalServerError();
                route.handle(httpRequest, httpResponse);
                return httpResponse;
            }
        }
        return HttpResponse.methodNotAllowed();
    }
}
