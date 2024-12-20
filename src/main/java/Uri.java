public class Uri {
    private final String uri;
    private Path path;
    private QueryString queryString;

    public static Uri parse(String uri) {
        return new Uri(uri);
    }

    private Uri(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri cannot be null");
        }

        this.uri = uri;

        initializeUriParts();
    }

    private void initializeUriParts() {
        if (!uri.contains("?")) {
            path = new Path(uri);
            queryString = QueryString.empty();
            return;
        }

        String[] uriParts = uri.split("\\?");
        path = new Path(uriParts[0]);
        queryString = QueryString.from(uriParts[1]);
    }

    public Path path() {
        return path;
    }

    public QueryParameters queryParameters() {
        return queryString.queryParameters();
    }
}
