public class HttpResponse {
    private int statusCode;
    private ResponseBody responseBody;

    public static HttpResponse ok() {
        return ok("OK");
    }

    public static HttpResponse ok(String body) {
        return new HttpResponse(200, new ResponseBody(body));
    }

    public static HttpResponse created() {
        return new HttpResponse(201, new ResponseBody("Created"));
    }

    public static HttpResponse notFound() {
        return new HttpResponse(404, new ResponseBody("Not Found"));
    }

    private HttpResponse(int statusCode, ResponseBody responseBody) {
        if (responseBody == null) {
            throw new IllegalArgumentException("Response body cannot be null");
        }

        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public void convertTo(HttpResponse httpResponse) {
        statusCode = httpResponse.statusCode;
        responseBody = httpResponse.responseBody;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        appendLineWith(result, "HTTP/1.1", String.valueOf(statusCode), responseBody.toString());
        appendLineWith(result, "Content-Type: text/plain");
        appendLineWith(result, "Content-Length:", String.valueOf(responseBody.length()));
        appendLineWith(result);
        appendLineWith(result, responseBody.toString());

        return result.toString();
    }

    private void appendLineWith(StringBuilder builder, String... subStrings) {
        for (String subString : subStrings) {
            builder.append(subString)
                    .append(" ");
        }

        // If there were sub-strings...
        if (subStrings.length > 0) {
            // ...remove last space
            builder.deleteCharAt(builder.length() - 1);
        }

        builder.append(System.lineSeparator());
    }
}
