import java.util.Objects;

public class Path {
    private final String path;

    public Path(String path) {
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

    @Override
    public String toString() {
        return path;
    }
}
