import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        Runnable task = Main::listenHttpAndRespond;
        Thread thread = new Thread(task);
        thread.start();
    }

    private static void listenHttpAndRespond() {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is listening on port 8080");
            while (true) {
                handleClientRequest(serverSocket);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        return new HttpRequest(reader.readLine());
    }

    private static void responseToClient(Socket socket, HttpRequest requestLine) throws IOException {
        String endpoint = requestLine.endpoint();

        HttpResponse response;
        if (endpoint.equals("/hello")) {
            response = createHttpOkResponse();
        } else {
            response = createHttpNotFoundResponse();
        }
        writeHttpResponse(socket, response);
    }

    private static HttpResponse createHttpOkResponse() {
        return createHttpResponse(200, new ResponseBody("OK"));
    }

    private static HttpResponse createHttpNotFoundResponse() {
        return createHttpResponse(404, new ResponseBody("Not Found"));
    }

    private static HttpResponse createHttpResponse(int statusCode, ResponseBody responseBody) {
        return new HttpResponse(statusCode, responseBody);
    }

    private static void writeHttpResponse(Socket socket, HttpResponse response) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(response);
    }
}
