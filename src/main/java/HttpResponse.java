public class HttpResponse {
    private final HttpStatus status;
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
        return new HttpResponse(HttpStatus.NOT_FOUND);
    }

    public static HttpResponse methodNotAllowed() {
        return new HttpResponse(405, new ResponseBody("Method not allowed"));
    }

    public static HttpResponse internalServerError() {
        return new HttpResponse(500, new ResponseBody("Internal Server Error"));
    }

    public static HttpResponse internalServerError(String exceptionMessage) {
        return new HttpResponse(500, new ResponseBody("Internal Server Error (exception message: '" + exceptionMessage + "')"));
    }

    private HttpResponse(HttpStatus status) {
        this.status = status;
        this.responseBody = ResponseBody.empty();
    }

    private HttpResponse(int statusCode, ResponseBody responseBody) {
        if (responseBody == null) {
            throw new IllegalArgumentException("Response body cannot be null");
        }

        this.statusCode = statusCode;
        this.responseBody = responseBody;
        status = HttpStatus.OK;
    }

    public void convertTo(HttpResponse httpResponse) {
        statusCode = httpResponse.statusCode;
        responseBody = httpResponse.responseBody;
    }

    @Override
    public String toString() {
        if (status == HttpStatus.NOT_FOUND) {
            StringBuilder result = new StringBuilder();
            appendLineWith(result, "HTTP/1.1", String.valueOf(status.code()), status.reason());
            appendLineWith(result, "Content-Type: text/plain");
            appendLineWith(result, "Content-Length:", String.valueOf(responseBody.length()));
            appendLineWith(result);
            appendLineWith(result, responseBody.toString());

            return result.toString();
        }

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
