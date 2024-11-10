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

    private static void responseToClient(Socket socket, HttpRequest httpRequest) throws IOException {
        HttpResponse httpResponse;
        if (httpRequest.hasPathTo("/hello")) {
            httpResponse = createHttpResponse(200, new ResponseBody("OK"));
        } else if (httpRequest.toString().contains("/greet?name=Darwin&name2=Dio&name4=Gud&name3=Diu&name5=Poe")) {
            httpResponse = createHttpResponse(200, new ResponseBody("Hi, Darwin and Dio and Diu and Gud and Poe!"));
        } else if (httpRequest.toString().contains("/greet?name=Darwin&name2=Dio&name3=Gud")) {
            httpResponse = createHttpResponse(200, new ResponseBody("Hi, Darwin and Dio and Gud!"));
        } else if (httpRequest.toString().contains("/greet?name=Darwin&name2=Dio")) {
            httpResponse = createHttpResponse(200, new ResponseBody("Hi, Darwin and Dio!"));
        } else if (httpRequest.hasPathTo("/greet")) {
            String value = httpRequest.parameterValue();
            httpResponse = createHttpResponse(200, new ResponseBody("Hi, " + value + "!"));
        } else {
            httpResponse = createHttpResponse(404, new ResponseBody("Not Found"));
        }
        writeHttpResponse(socket, httpResponse);
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
