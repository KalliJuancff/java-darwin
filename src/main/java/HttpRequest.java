public class HttpRequest {
    private final String requestLine;
    private Uri uri;
    private String method;

    // Example: requestLine := 'GET /sum?x=1&y2=z=4 HTTP/1.1'
    public static HttpRequest from(String requestLine) {
        return new HttpRequest(requestLine);
    }

    private HttpRequest(String requestLine) {
        if (requestLine == null) {
            throw new IllegalArgumentException("Request line cannot be null");
        }

        this.requestLine = requestLine;

        initializeHttpRequestParts(requestLine);
    }

    private void initializeHttpRequestParts(String requestLine) {
        String[] requestLineParts = requestLine.split(" ");
        method = requestLineParts[0];
        uri = Uri.from(requestLineParts[1]);
    }

    public boolean isGetMethod() {
        return method.equals(HttpMethods.GET);
    }

    public boolean isPostMethod() {
        return method.equals(HttpMethods.POST);
    }

    public boolean isDeleteMethod() {
        return method.equals(HttpMethods.DELETE);
    }

    public boolean hasPathEqualTo(String path) {
        return uri.hasPathEqualTo(path);
    }

    public QueryParameters queryParameters() {
        return uri.queryParameters();
    }

    @Override
    public String toString() {
        return requestLine;
    }
}
