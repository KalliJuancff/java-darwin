import java.util.Objects;

public class EndPoint {
    private final String endpoint;

    public EndPoint(String endpoint) {
        if (endpoint == null) {
            throw new IllegalArgumentException("Endpoint cannot be null");
        }

        this.endpoint = endpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndPoint endPoint = (EndPoint) o;
        return Objects.equals(endpoint, endPoint.endpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(endpoint);
    }
}
