import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parameters implements Iterable<QueryParameter> {
    private final List<QueryParameter> queryParameters = new ArrayList<>();

    public static Parameters empty() {
        return new Parameters();
    }

    public static Parameters from(String request) {
        Parameters result = new Parameters();

        // Example: request := 'GET /greet?x=1&y2=z=4 HTTP/1.1'
        String uri = request.split(" ")[1];
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
