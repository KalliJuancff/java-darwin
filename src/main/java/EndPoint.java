public class EndPoint {
    private final HttpMethod method;
    private final Path path;

    public EndPoint(HttpMethod method, String path) {
        if (method == null) {
            throw new IllegalArgumentException("Method cannot be null");
        }
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }

        this.method = method;
        this.path = new Path(path);
    }

    public Path path() {
        return path;
    }
}
