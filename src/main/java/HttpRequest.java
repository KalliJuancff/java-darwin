public class HttpRequest {
    private final String request;

    public HttpRequest(String request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        this.request = request;
    }

    public EndPoint endpoint() {
        return EndPoint.from(this);
    }

    public String parameterValue() {
        try {
            return request.split("\\?")[1].split("=")[1].split(" ")[0];
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String toString() {
        return request;
    }
}
