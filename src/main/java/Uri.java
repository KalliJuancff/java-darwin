public class Uri {
    private final String uri;
    private Path path;
    private QueryString queryString;

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

        if (uriParts.length > 1) {
            queryString = new QueryString(uriParts[1]);
        } else {
            queryString = new QueryString("");
        }
    }

    public boolean hasPathEqualTo(Path path) {
        return this.path.equals(path);
    }

    public QueryParameters queryParameters() {
        return queryString.queryParameters();
    }
}
