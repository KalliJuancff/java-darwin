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

    public Parameter[] parameters() {
        Parameter[] parameters = new Parameter[1];
        parameters[0] = new Parameter("name", request.split("\\?")[1].split("=")[1].split(" ")[0]);
        return parameters;
    }

    @Override
    public String toString() {
        return request;
    }
}
