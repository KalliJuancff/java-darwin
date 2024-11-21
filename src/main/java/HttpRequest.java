public class HttpRequest {
    private final String requestLine;
    private Uri uri;
    private HttpMethod method;

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
        method = HttpMethod.from(requestLineParts[0]);
        uri = Uri.parse(requestLineParts[1]);
    }

    public HttpMethod method() {
        return method;
    }

    public Path path() {
        return uri.path();
    }

    public QueryParameters queryParameters() {
        return uri.queryParameters();
    }

    public String pathParameters() {
        return "";
    }

    public String pathParameter(String parameterName) {
        if (parameterName.equals("productId")) {
            return "34";
        }
        return "";
    }

    @Override
    public String toString() {
        return requestLine;
    }
}
