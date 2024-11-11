import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueryParameters implements Iterable<QueryParameter> {
    private final List<QueryParameter> queryParameters = new ArrayList<>();

    public static QueryParameters empty() {
        return new QueryParameters();
    }

    public static QueryParameters from(String uri) {
        QueryParameters result = new QueryParameters();

        // Example: request := 'GET /greet?x=1&y2=z=4 HTTP/1.1'
        String parametersAsString = uri.split("\\?")[1];
        String[] parametersAndValues = parametersAsString.split("&");
        for (String parameterAndValue : parametersAndValues) {
            String[] parameter = parameterAndValue.split("=");
            String key = parameter[0];
            String value = parameter[1];

            result.add(key, value);
        }

        return result;
    }

    private void add(String key, String value) {
        queryParameters.add(new QueryParameter(key, value));
    }

    @Override
    public Iterator<QueryParameter> iterator() {
        return queryParameters.iterator();
    }
}
