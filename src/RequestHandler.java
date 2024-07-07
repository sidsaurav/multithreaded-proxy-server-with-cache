import java.io.*;
import java.net.*;

class RequestHandler implements Runnable {
    private final Socket clientSocket;
    private final LRUCache cache;

    public RequestHandler(Socket socket, LRUCache cache) {
        this.clientSocket = socket;
        this.cache = cache;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String request = in.readLine();
            if (request != null && request.startsWith("GET")) {
                String apiUrl = extractApiUrl(request);
                String response = getResponseWithCaching(apiUrl);
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: application/json");
                out.println();
                out.println(response);
            } else {
                out.println("HTTP/1.1 400 Bad Request");
            }
        } catch (IOException e) {
            System.err.println("Error handling client request: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private String extractApiUrl(String request) {
        String[] parts = request.split(" ");
        if (parts.length > 1) {
            return parts[1].substring(1); // Remove leading '/'
        }
        return "";
    }

    private String getResponseWithCaching(String apiUrl) {
        String cachedResponse = cache.get(apiUrl);
        if (cachedResponse != null) {
            System.out.println("Cache HIT for: " + apiUrl);
            return cachedResponse;
        }
        System.out.println("Cache MISS for: " + apiUrl);
        String response = makeApiCall(apiUrl);
        cache.put(apiUrl, response);
        return response;
    }

    private String makeApiCall(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            return content.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}