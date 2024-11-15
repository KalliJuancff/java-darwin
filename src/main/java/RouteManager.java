import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class RouteManager {
    private final List<String> paths = new ArrayList<>();
    private final Map<String, Routes> routes = Map.of(
            HttpMethods.GET.method(), new Routes(),
            HttpMethods.POST.method(), new Routes(),
            HttpMethods.DELETE.method(), new Routes()
    );

    public void addGetRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        paths.add(path);
        routes.get(HttpMethods.GET.method()).add(new Route(path, handler));
    }

    public void addPostRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        paths.add(path);
        routes.get(HttpMethods.POST.method()).add(new Route(path, handler));
    }

    public void addDeleteRoute(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        paths.add(path);
        routes.get(HttpMethods.DELETE.method()).add(new Route(path, handler));
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
