public class HttpRequest {
    private final String requestLine;

    public HttpRequest(String requestLine) {
        if (requestLine == null) {
            throw new IllegalArgumentException("Request line cannot be null");
        }

        this.requestLine = requestLine;
    }

    public boolean hasPathTo(String path) {
        return Path.from(this).equals(Path.of(path));
    }

    public Parameters parameters() {
        if (!hasQueryString()) {
            return Parameters.empty();
        }

        return Parameters.from(requestLine);
    }

    private boolean hasQueryString() {
        return requestLine.contains("?");
    }

    @Override
    public String toString() {
        return requestLine;
    }
}
