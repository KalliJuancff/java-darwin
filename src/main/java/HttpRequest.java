public class HttpRequest {
    private final String request;

    public HttpRequest(String request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        this.request = request;
    }

    public String endpoint() {
        return request.split(" ")[1];
    }

    @Override
    public String toString() {
        return "Http request: '" + request + "'";
    }
}
