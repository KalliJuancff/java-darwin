public class Main {
    public static void main(String[] args) {
        Application app = new Application();

        app.get("/hello", (req, res) -> {
            if (req.isPostMethod()) {
                res.convertTo(HttpResponse.created());
            } else {
                res.convertTo(HttpResponse.ok());
            }
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
