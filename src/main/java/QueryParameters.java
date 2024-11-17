import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueryParameters implements Iterable<QueryParameter> {
    private final List<QueryParameter> queryParameters = new ArrayList<>();

    public static QueryParameters empty() {
        return new QueryParameters();
    }

    public static QueryParameters parse(String queryString) {
        QueryParameters result = new QueryParameters();
        populateQueryParameters(queryString, result);

        return result;
    }

    private static void populateQueryParameters(String queryString, QueryParameters queryParameters) {
        String[] paramsAndValues = queryString.split("&");
        for (String paramAndValue : paramsAndValues) {
            String[] tokens = paramAndValue.split("=");
            String key = tokens[0];
            String value = tokens[1];

            queryParameters.add(key, value);
        }
    }

    private void add(String key, String value) {
        queryParameters.add(new QueryParameter(key, value));
    }

    @Override
    public Iterator<QueryParameter> iterator() {
        return queryParameters.iterator();
    }
}
