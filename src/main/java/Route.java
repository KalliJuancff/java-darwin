import java.util.function.BiConsumer;

public class Route {
    private final String path;
    private final BiConsumer<HttpRequest, HttpResponse> handler;

    public Route(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        this.path = path;
        this.handler = handler;
    }

    public boolean matches(HttpRequest httpRequest) {
        return httpRequest.hasPathEqualTo(path);
    }

    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        handler.accept(httpRequest, httpResponse);
    }
}
