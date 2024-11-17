public class ResponseBody {
    private final String body;

    public static ResponseBody empty() {
        return new ResponseBody("");
    }

    // TODO: change access modifier to private
    public ResponseBody(String body) {
        if (body == null) {
            throw new IllegalArgumentException("Body cannot be null");
        }

        this.body = body;
    }

    public int length() {
        return body.length();
    }

    @Override
    public String toString() {
        return body;
    }
}
