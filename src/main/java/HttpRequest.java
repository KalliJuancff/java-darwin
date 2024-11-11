public class HttpRequest {
    private final String requestLine;
    private final String[] requestLineParts;
    private final Uri uri;

    public HttpRequest(String requestLine) {
        if (requestLine == null) {
            throw new IllegalArgumentException("Request line cannot be null");
        }

        this.requestLine = requestLine;
        requestLineParts = requestLine.split(" ");
        uri = new Uri(requestLineParts[1]);
    }

    public boolean isPathEqualTo(String path) {
        return Path.from(requestLine).equals(Path.of(path));
    }

    public QueryParameters queryParameters() {
        if (!uri.hasQueryString()) {
            return QueryParameters.empty();
        }

        return QueryParameters.from(requestLine);
    }

    @Override
    public String toString() {
        return requestLine;
    }
}
