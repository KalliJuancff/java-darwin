public class Uri {
    private final String uri;

    public Uri(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri cannot be null");
        }

        this.uri = uri;
    }

    public Path path() {
        if (hasQueryString()) {
            String[] uriParts = uri.split("\\?");
            String path = uriParts[0];
            return new Path(path);
        }

        return new Path(uri);
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
