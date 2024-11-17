public class HttpResponse {
    private HttpStatus status;
    private ResponseBody responseBody;

    public static HttpResponse ok() {
        return new HttpResponse(HttpStatus.OK);
    }

    public static HttpResponse ok(String body) {
        return new HttpResponse(HttpStatus.OK, body);
    }

    public static HttpResponse created() {
        return new HttpResponse(HttpStatus.CREATED);
    }

    public static HttpResponse notFound() {
        return new HttpResponse(HttpStatus.NOT_FOUND);
    }

    public static HttpResponse methodNotAllowed() {
        return new HttpResponse(HttpStatus.METHOD_NOT_ALLOWED);
    }

    public static HttpResponse internalServerError() {
        return new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    public static HttpResponse internalServerError(String exceptionMessage) {
        return new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error: '" + exceptionMessage + "'");
    }

    private HttpResponse(HttpStatus httpStatus) {
        this.status = httpStatus;
        this.responseBody = ResponseBody.empty();
    }

    private HttpResponse(HttpStatus httpStatus, String body) {
        if (body == null) {
            throw new IllegalArgumentException("Response body cannot be null");
        }

        this.status = httpStatus;
        this.responseBody = new ResponseBody(body);
    }

    public void convertTo(HttpResponse httpResponse) {
        status = httpResponse.status;
        responseBody = httpResponse.responseBody;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        appendLineWith(result, "HTTP/1.1", String.valueOf(status.code()), status.reason());
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
