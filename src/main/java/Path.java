import java.util.Objects;

public class Path {
    private final String path;

    public static Path from(String requestLine) {
        String[] requestLineParts = requestLine.split(" ");
        var uri = requestLineParts[1];
        if (uri.contains("?")) {
            return new Path(uri.split("\\?")[0]);
        }
        return new Path(uri);
    }

    public static Path of(String path) {
        return new Path(path);
    }

    private Path(String path) {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }

        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return Objects.equals(this.path, path.path);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path);
    }
}
