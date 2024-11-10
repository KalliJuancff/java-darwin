public class HttpRequest {
    private final String request;

    public HttpRequest(String request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        this.request = request;
    }

    public EndPoint endpoint() {
        var result = request.split(" ")[1];
        if (result.contains("?")) {
            return new EndPoint(result.split("\\?")[0]);
        }
        return new EndPoint(result);
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
