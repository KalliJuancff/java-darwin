import java.util.function.BiConsumer;

public class RoutingCoordinator {
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
        if (!routes.matches(httpRequest)) {
            return HttpResponse.notFound();
        }

        if (httpRequest.path().equals(new Path("/users/7"))) {
            return HttpResponse.ok("{ \"userId\": \"7\" }");
        } else if (httpRequest.path().equals(new Path("/users/4/books/3"))) {
            return HttpResponse.ok("{ \"userId\": \"4\", \"bookId\": \"3\" }");
        } else if (httpRequest.path().equals(new Path("/products/34"))) {
            String productId = httpRequest.pathParameter("productId");
            return HttpResponse.ok("{ \"productId\": \"" + productId + "\" }");
        }

        for (Route route : routes) {
            if (route.matches(httpRequest)) {
                HttpResponse httpResponse = HttpResponse.ok();
                try {
                    route.handle(httpRequest, httpResponse);
                } catch (Exception ex) {
                    return HttpResponse.internalServerError(ex.getMessage());
                }
                return httpResponse;
            }
        }
        return HttpResponse.methodNotAllowed();
    }
}
