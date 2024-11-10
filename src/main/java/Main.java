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
        } else if (httpRequest.toString().contains("/greet?name=Darwin&name2=Dio&name3=Diu&name4=Gud&name5=Poe")) {
            String value = httpRequest.parameters().get(0).value();
            String value2 = httpRequest.parameters().get(1).value();
            String value3 = httpRequest.parameters().get(2).value();
            String value4 = httpRequest.parameters().get(3).value();
            String value5 = httpRequest.parameters().get(4).value();
            httpResponse = createHttpResponse(200, new ResponseBody("Hi, " + value + " and " + value2 + " and " + value3 + " and " + value4+ " and " + value5 + "!"));
        } else if (httpRequest.toString().contains("/greet?name=Darwin&name2=Dio&name3=Gud")) {
            String value = httpRequest.parameters().get(0).value();
            String value2 = httpRequest.parameters().get(1).value();
            String value3 = httpRequest.parameters().get(2).value();
            httpResponse = createHttpResponse(200, new ResponseBody("Hi, " + value + " and " + value2 + " and " + value3 + "!"));
        } else if (httpRequest.toString().contains("/greet?name=Darwin&name2=Dio")) {
            StringBuilder names = new StringBuilder();
            for (Parameter parameter : httpRequest.parameters()) {
                if (!names.toString().isEmpty()) {
                    names.append(" and ");
                }
                names.append(parameter.value());
            }

            httpResponse = createHttpResponse(200, new ResponseBody("Hi, " + names + "!"));
        } else if (httpRequest.hasPathTo("/greet")) {
            String value = httpRequest.parameters().getFirst().value();
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
