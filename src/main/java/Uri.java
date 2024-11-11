public class Uri {
    private final String uri;

    public Uri(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri cannot be null");
        }

        this.uri = uri;
    }

    public Path path() {
        if (!hasQueryString()) {
            return Path.of(uri);
        }

        String[] uriParts = uri.split("\\?");
        String path = uriParts[0];
        return Path.of(path);
    }

    public QueryParameters queryParameters() {
        if (!hasQueryString()) {
            return QueryParameters.empty();
        }

        return QueryParameters.from(uri);
    }

    private boolean hasQueryString() {
        return uri.contains("?");
    }
}
