package com.devops.ci;
 
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
 
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
 
public class App {
 
    public static void main(String[] args) throws IOException {
 
        // Bind to all interfaces (IMPORTANT for Docker)
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8080), 0);
 
        server.createContext("/", (HttpExchange exchange) -> {
 
            String response = "CI/CD App Running 🚀";
 
            // Convert to bytes properly
            byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
 
            // Send correct content length
            exchange.sendResponseHeaders(200, responseBytes.length);
 
            // Write response safely
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        });
 
        server.start();
 
        System.out.println("Server started on port 8080");
    }
}
