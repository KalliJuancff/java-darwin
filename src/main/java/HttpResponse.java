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
        StringBuilder result = new StringBuilder();
        appendLineToStringBuilder(result, "HTTP/1.1", String.valueOf(statusCode), responseBody.toString());
        appendLineToStringBuilder(result, "Content-Type: text/plain");
        appendLineToStringBuilder(result, "Content-Length:", String.valueOf(responseBody.length()));
        result.append(System.lineSeparator());
        appendLineToStringBuilder(result, responseBody.toString());
        return result.toString();
    }

    private void appendLineToStringBuilder(StringBuilder builder, String... subStrings) {
        for (String subString : subStrings) {
            builder.append(subString)
                    .append(" ");
        }

        // Remove last space
        builder.deleteCharAt(builder.length() - 1);

        builder.append(System.lineSeparator());
    }
}
