public class HttpResponse {
    private final int statusCode;
    private final ResponseBody responseBody;
    private StringBuilder result;

    public HttpResponse(int statusCode, ResponseBody responseBody) {
        if (responseBody == null) {
            throw new IllegalArgumentException("Response body cannot be null");
        }

        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        result = new StringBuilder();
        appendLineWith("HTTP/1.1", String.valueOf(statusCode), responseBody.toString());
        appendLineWith("Content-Type: text/plain");
        appendLineWith("Content-Length:", String.valueOf(responseBody.length()));
        appendLineWith();
        appendLineWith(responseBody.toString());
        return result.toString();
    }

    private void appendLineWith(String... subStrings) {
        for (String subString : subStrings) {
            result.append(subString)
                    .append(" ");
        }

        // If there were sub-strings...
        if (subStrings.length > 0) {
            // ...remove last space
            result.deleteCharAt(result.length() - 1);
        }

        result.append(System.lineSeparator());
    }
}
