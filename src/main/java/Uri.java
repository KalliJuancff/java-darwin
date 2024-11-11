public class Uri {
    private final String uri;

    public Uri(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri cannot be null");
        }

        this.uri = uri;
    }

    public boolean hasQueryString() {
        return uri.contains("?");
    }
}
