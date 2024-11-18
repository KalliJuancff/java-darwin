public class Main {
    public static void main(String[] args) {
        Application app = new Application();

        app.get("/hello", (req, res) -> {
            res.convertTo(HttpResponse.ok());
        });
        app.post("/hello", (req, res) -> {
            res.convertTo(HttpResponse.created());
        });
        app.get("/greet", (req, res) -> {
            String names = extractNamesFrom(req);
            res.convertTo(HttpResponse.ok("Hi, " + names + "!"));
        });
        app.get("/boom", (req, res) -> {
            throw new RuntimeException("Boom! (simulated internal error)");
        });

        app.listen(8080);
    }

    private static String extractNamesFrom(HttpRequest httpRequest) {
        var names = new StringBuilder();
        for (QueryParameter queryParameter : httpRequest.queryParameters()) {
            if (!names.toString().isEmpty()) {
                names.append(" and ");
            }
            names.append(queryParameter.value());
        }
        return names.toString();
    }
}
