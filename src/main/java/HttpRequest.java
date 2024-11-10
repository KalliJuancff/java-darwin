public class HttpRequest {
    private final String request;

    public HttpRequest(String request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        this.request = request;
    }

    public String endpoint() {
        var result = request.split(" ")[1];
        if (result.contains("?")) {
            return result.split("\\?")[0];
        }
        return result;
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
