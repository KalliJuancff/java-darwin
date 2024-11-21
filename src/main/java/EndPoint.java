public class EndPoint {
    private final HttpMethod method;
    private final Path path;

    public EndPoint(HttpMethod method, Path path) {
        if (method == null) {
            throw new IllegalArgumentException("Method cannot be null");
        }
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }

        this.method = method;
        this.path = path;
    }

    public Path path() {
        return path;
    }

    public boolean matches(HttpRequest httpRequest) {
        return httpRequest.path().equals(path) && httpRequest.method().equals(method);
    }
}
