public class QueryString {
    private final String queryString;

    public static QueryString empty() {
        return new QueryString("");
    }

    public static QueryString from(String queryString) {
        return new QueryString(queryString);
    }

    private QueryString(String queryString) {
        if (queryString == null) {
            throw new IllegalArgumentException("Query string cannot be null");
        }

        this.queryString = queryString;
    }

    public QueryParameters queryParameters() {
        if (queryString.isBlank()) {
            return QueryParameters.empty();
        }

        return QueryParameters.from(queryString);
    }

    @Override
    public String toString() {
        return queryString;
    }
}
