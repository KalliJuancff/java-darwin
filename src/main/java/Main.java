public class Main {
    public static void main(String[] args) {
        Application app = new Application(8080);

        app.get("/hello", (req, res) -> {
            res.convertTo(HttpResponse.ok());
        });
        app.post("/hello", (req, res) -> {
            res.convertTo(HttpResponse.created());
        });
        app.get("/greet", (req, res) -> {
            StringBuilder names = extractNamesFrom(req);
            res.convertTo(HttpResponse.ok("Hi, " + names + "!"));
        });

        app.run();
    }

    private static StringBuilder extractNamesFrom(HttpRequest httpRequest) {
        StringBuilder names = new StringBuilder();
        for (QueryParameter queryParameter : httpRequest.queryParameters()) {
            if (!names.toString().isEmpty()) {
                names.append(" and ");
            }
            names.append(queryParameter.value());
        }
        return names;
    }
}
