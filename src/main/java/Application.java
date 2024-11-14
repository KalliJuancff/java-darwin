import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.BiConsumer;

public class Application {
    public void run() {
        Runnable task = Application::listenHttpAndRespond;
        Thread thread = new Thread(task);
        thread.start();
    }

    private static void listenHttpAndRespond() {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is listening on port 8080");

            get("/hello", (req, res) -> {
                if (req.isPostMethod()) {
                    res.convertTo(HttpResponse.created());
                } else {
                    res.convertTo(HttpResponse.ok());
                }
            });

            while (true) {
                handleClientRequest(serverSocket);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static Route[] routes = new Route[1];

    private static void get(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        routes[0] = new Route(path, handler);
    }

    private static void handleClientRequest(ServerSocket serverSocket) throws IOException {
        try (Socket socket = serverSocket.accept()) {
            HttpRequest httpRequest = readHttpRequest(socket);
            System.out.println("HTTP request received: '" + httpRequest + "'");
            responseToClient(socket, httpRequest);
        }
    }

    private static HttpRequest readHttpRequest(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        return HttpRequest.from(reader.readLine());
    }

    private static void responseToClient(Socket socket, HttpRequest httpRequest) throws IOException {
        HttpResponse httpResponse = createHttpResponse(httpRequest);
        writeHttpResponse(socket, httpResponse);
    }

    private static HttpResponse createHttpResponse(HttpRequest httpRequest) {
//        if (httpRequest.hasPathEqualTo("/hello")) {
//            if (httpRequest.isPostMethod()) {
//                return HttpResponse.created();
//            }
//            return HttpResponse.ok();
//        }

        for (Route route : routes) {
            if (route.matches(httpRequest)) {
                HttpResponse httpResponse = HttpResponse.notFound();
                route.handle(httpRequest, httpResponse);
                return httpResponse;
            }
        }

        if (httpRequest.hasPathEqualTo("/greet")) {
            StringBuilder names = extractNamesFrom(httpRequest);
            return HttpResponse.ok("Hi, " + names + "!");
        }
        return HttpResponse.notFound();
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

    private static void writeHttpResponse(Socket socket, HttpResponse response) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(response);
    }
}
