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
                try (Socket socket = serverSocket.accept()) {
                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    String requestLine = reader.readLine();
                    System.out.println("Received: " + requestLine);

                    OutputStream output = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(output, true);
                    var endpoint = requestLine.split(" ")[1];
                    if (endpoint.equals("/hello")) {
                        writer.println("HTTP/1.1 200 OK");
                    } else {
                        writer.println("HTTP/1.1 404 Not Found");
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
