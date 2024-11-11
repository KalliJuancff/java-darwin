public record QueryParameter(String key, String value) {
    public QueryParameter {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

    }
}
