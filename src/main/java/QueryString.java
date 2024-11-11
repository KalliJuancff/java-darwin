public class QueryString {
    private final String queryString;

    public QueryString(String queryString) {
        if (queryString == null) {
            throw new IllegalArgumentException("Query string cannot be null");
        }

        this.queryString = queryString;
    }

    public QueryParameters queryParameters() {
        if (queryString.isBlank()) {
            return QueryParameters.empty();
        }

        return QueryParameters.from(this);
    }

    @Override
    public String toString() {
        return queryString;
    }
}
