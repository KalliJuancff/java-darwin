public class HttpRequest {
    private final String request;

    public HttpRequest(String request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        this.request = request;
    }

    public boolean endPointsTo(String endPoint) {
        return EndPoint.from(this).equals(EndPoint.of(endPoint));
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
