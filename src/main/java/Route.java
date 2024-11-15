import java.util.function.BiConsumer;

public class Route {
    private final EndPoint endpoint;
    private final BiConsumer<HttpRequest, HttpResponse> handler;

    public Route(EndPoint endpoint, BiConsumer<HttpRequest, HttpResponse> handler) {
        if (endpoint == null) {
            throw new IllegalArgumentException("Endpoint cannot be null");
        }
        if (handler == null) {
            throw new IllegalArgumentException("Handler cannot be null");
        }

        this.endpoint = endpoint;
        this.handler = handler;
    }

    public Path path() {
        return endpoint.path();
    }

    public boolean matches(HttpRequest httpRequest) {
        return endpoint.matches(httpRequest);
    }

    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        handler.accept(httpRequest, httpResponse);
    }
}
