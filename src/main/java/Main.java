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
            HttpRequest requestLine = readHttpRequest(socket);
            System.out.println("Received: '" + requestLine + "'");
            responseToClient(socket, requestLine);
        }
    }

    private static HttpRequest readHttpRequest(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        return new HttpRequest(reader.readLine());
    }

    private static void responseToClient(Socket socket, HttpRequest requestLine) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        String endpoint = requestLine.endpoint();
        if (endpoint.equals("/hello")) {
            responseToClient(writer, 200, new ResponseBody("OK"));
        } else {
            responseToClient(writer, 404, new ResponseBody("Not Found"));
        }
    }

    private static void responseToClient(PrintWriter writer, int statusCode, ResponseBody responseBody) {
        writer.println(new HttpResponse(statusCode, responseBody));
    }
}
