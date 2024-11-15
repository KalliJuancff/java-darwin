import java.util.function.BiConsumer;

public class RouteManager {
    private final Routes getRoutes = new Routes();
    private final Routes postRoutes = new Routes();
    private final Routes deleteRoutes = new Routes();

    public void addGetRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        getRoutes.add(new Route(path, handler));
    }

    public void addPostRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        postRoutes.add(new Route(path, handler));
    }

    public void addDeleteRoute(String path, BiConsumer<HttpRequest,HttpResponse> handler) {
        deleteRoutes.add(new Route(path, handler));
    }

    public HttpResponse responseTo(HttpRequest httpRequest) {
        if (httpRequest.isDeleteMethod()) {
            for (Route route : deleteRoutes) {
                if (route.matches(httpRequest)) {
                    HttpResponse httpResponse = HttpResponse.internalServerError();
                    route.handle(httpRequest, httpResponse);
                    return httpResponse;
                }
            }

            return HttpResponse.methodNotAllowed();
        }
        if (httpRequest.isPostMethod()) {
            for (Route route : postRoutes) {
                if (route.matches(httpRequest)) {
                    HttpResponse httpResponse = HttpResponse.internalServerError();
                    route.handle(httpRequest, httpResponse);
                    return httpResponse;
                }
            }
        }
        if (httpRequest.isGetMethod()) {
            for (Route route : getRoutes) {
                if (route.matches(httpRequest)) {
                    HttpResponse httpResponse = HttpResponse.internalServerError();
                    route.handle(httpRequest, httpResponse);
                    return httpResponse;
                }
            }
        }
        return HttpResponse.notFound();
    }
}
