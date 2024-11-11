public class HttpRequest {
    private final String requestLine;
    private final Uri uri;

    public HttpRequest(String requestLine) {
        if (requestLine == null) {
            throw new IllegalArgumentException("Request line cannot be null");
        }

        this.requestLine = requestLine;

        String[] requestLineParts = requestLine.split(" ");
        uri = new Uri(requestLineParts[1]);
    }

    public boolean isPathEqualTo(String path) {
        return uri.path().equals(Path.of(path));
    }

    public QueryParameters queryParameters() {
        return uri.queryParameters();
    }

    @Override
    public String toString() {
        return requestLine;
    }
}
