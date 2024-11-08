public class HttpResponse {
    private final int statusCode;
    private final ResponseBody responseBody;

    public HttpResponse(int statusCode, ResponseBody responseBody) {
        if (responseBody == null) {
            throw new IllegalArgumentException("Response body cannot be null");
        }

        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1")
                .append(" ")
                .append(statusCode)
                .append(" ")
                .append(responseBody)
                .append(System.lineSeparator());
        response.append("Content-Type: text/plain")
                .append(System.lineSeparator());
        response.append("Content-Length:")
                .append(" ")
                .append(responseBody.length())
                .append(System.lineSeparator());
        response.append(System.lineSeparator());
        response.append(responseBody)
                .append(System.lineSeparator());
        return response.toString();
    }
}