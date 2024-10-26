package at.technikum.mtcg.server;

import at.technikum.mtcg.handlers.SessionHandler;
import at.technikum.mtcg.handlers.UserHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class RestServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(10001), 0);
        server.createContext("/users", new UserHandler());
        server.createContext("/sessions", new SessionHandler());
        server.setExecutor(null);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Stopping server...");
            server.stop(0);
            System.out.println("Server stopped.");
        }));

        server.start();
        System.out.println("Server running on port 10001...");
    }
}
