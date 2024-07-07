import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ProxyServer {
    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 10;
    private static final int CACHE_SIZE = 100;
    private static LRUCache cache;

    public static void main(String[] args) throws IOException {
        cache = new LRUCache(CACHE_SIZE);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Proxy Server is running on port " + PORT);

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                executor.execute(new RequestHandler(clientSocket, cache));
            } catch (IOException e) {
                System.err.println("Error accepting client connection: " + e.getMessage());
            }
        }
    }
}