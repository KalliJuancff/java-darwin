public class HttpRequest {
    private final String requestLine;
    private final Uri uri;

    // Example: requestLine := 'GET /greet?x=1&y2=z=4 HTTP/1.1'
    public HttpRequest(String requestLine) {
        if (requestLine == null) {
            throw new IllegalArgumentException("Request line cannot be null");
        }

        this.requestLine = requestLine;

        String[] requestLineParts = requestLine.split(" ");
        uri = new Uri(requestLineParts[1]);
    }

    public boolean isPathEqualTo(String path) {
        return uri.path().equals(new Path(path));
    }

    public QueryParameters queryParameters() {
        return uri.queryParameters();
    }

    @Override
    public String toString() {
        return requestLine;
    }
}
