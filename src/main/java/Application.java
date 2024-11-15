import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.BiConsumer;

public class Application {
    private final int port;
    private final Routes getRoutes = new Routes();
    private final Routes postRoutes = new Routes();

    public Application(int port) {
        this.port = port;
    }

    public void get(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        getRoutes.add(new Route(path, handler));
    }

    public void post(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        postRoutes.add(new Route(path, handler));
    }

    public void run() {
        post("/greet", (req, res) -> {
            res.convertTo(HttpResponse.methodNotAllowed());
        });

        Runnable task = this::listenHttpAndRespond;
        Thread thread = new Thread(task);
        thread.start();
    }

    private void listenHttpAndRespond() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                handleClientRequest(serverSocket);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void handleClientRequest(ServerSocket serverSocket) throws IOException {
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

    private void responseToClient(Socket socket, HttpRequest httpRequest) throws IOException {
        HttpResponse httpResponse = createHttpResponse(httpRequest);
        writeHttpResponse(socket, httpResponse);
    }

    private HttpResponse createHttpResponse(HttpRequest httpRequest) {
        if (!httpRequest.isPostMethod()) {
            for (Route route : getRoutes) {
                if (route.matches(httpRequest)) {
                    HttpResponse httpResponse = HttpResponse.internalServerError();
                    route.handle(httpRequest, httpResponse);
                    return httpResponse;
                }
            }
        } else {
            for (Route route : postRoutes) {
                if (route.matches(httpRequest)) {
                    HttpResponse httpResponse = HttpResponse.internalServerError();
                    route.handle(httpRequest, httpResponse);
                    return httpResponse;
                }
            }
        }
        return HttpResponse.notFound();
    }

    private static void writeHttpResponse(Socket socket, HttpResponse response) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(response);
    }
}
