import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HttpRequest {
    private final String request;

    public HttpRequest(String request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        this.request = request;
    }

    public boolean hasPathTo(String path) {
        return Path.from(this).equals(Path.of(path));
    }

    public List<Parameter> parameters() {
        // Example: request := 'GET /greet?x=1&y2=z=4 HTTP/1.1'
        if (!request.contains("?")) {
            return Collections.emptyList();
        }

        List<Parameter> result = new ArrayList<>();

        String uri = request.split(" ")[1];
        String parametersAsString = uri.split("\\?")[1];
        String[] parametersAndValues = parametersAsString.split("&");
        for (String parameterAndValue : parametersAndValues) {
            String[] parameter = parameterAndValue.split("=");
            String key = parameter[0];
            String value = parameter[1];

            result.add(new Parameter(key, value));
        }

        return result;
    }

    @Override
    public String toString() {
        return request;
    }
}
