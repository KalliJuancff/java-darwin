public class Uri {
    private final String uri;
    private Path path;
    private String queryString;

    public Uri(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri cannot be null");
        }

        this.uri = uri;

        initializeUriParts();
    }

    private void initializeUriParts() {
        String[] uriParts = uri.split("\\?");

        path = new Path(uriParts[0]);

        queryString = "";
        if (uriParts.length > 1) {
            queryString = uriParts[1];
        }
    }

    public Path path() {
        return path;
    }

    public String queryString() {
        return queryString;
    }

    public QueryParameters queryParameters() {
        if (!hasQueryString()) {
            return QueryParameters.empty();
        }

        return QueryParameters.from(queryString());
    }

    private boolean hasQueryString() {
        return uri.contains("?");
    }
}
