public class HttpRequest {
    private final String request;

    public HttpRequest(String request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        this.request = request;
    }

    public boolean hasPathTo(String path) {
        return Path.from(this).equals(Path.of(path));
    }

    public Parameters parameters() {
        if (!hasQueryString()) {
            return Parameters.empty();
        }

        return Parameters.from(request);
    }

    private boolean hasQueryString() {
        return request.contains("?");
    }

    @Override
    public String toString() {
        return request;
    }
}
