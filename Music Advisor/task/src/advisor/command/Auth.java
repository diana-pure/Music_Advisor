package advisor.command;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Auth implements Command {
    @Override
    public Status run() {
        final boolean[] authenticationSucceed = new boolean[1];
        final String[] code = new String[1];
        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.createContext("/",
                    exchange -> {
                        code[0] = exchange.getRequestURI().getQuery();
                        String bmessage;
                        if (code[0].startsWith("error")) {
                            bmessage = "Authorization code not found. Try again";
                            authenticationSucceed[0] = false;
                        } else {
                            bmessage = "Got the code. Return back to your program";
                            System.out.println("code received: " + code[0]);
                            authenticationSucceed[0] = true;
                        }
                        exchange.sendResponseHeaders(200, bmessage.length());
                        exchange.getResponseBody().write(bmessage.getBytes());
                        exchange.getResponseBody().close();
                    }

            );
            server.start();
            System.out.println("use this link to request the access code:");
            System.out.println("https://accounts.spotify.com/authorize?client_id=1ead1b4117aa43f3afe222fe6dded9c4&redirect_uri=http://localhost:8080&response_type=code");
            System.out.println("waiting for code...");
            while (code[0] == null) {
                Thread.sleep(1);
            }
            server.stop(1);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (!authenticationSucceed[0]) {
            return Status.FAILED;
        }

        System.out.println("making http request for access_token...");

        HttpRequest request = HttpRequest.newBuilder()
                .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", "Basic MWVhZDFiNDExN2FhNDNmM2FmZTIyMmZlNmRkZWQ5YzQ6ZjNkNTk1N2UyMjNmNGNiNGE3ZWVkZGZlZDRlMDc1ZGY=")
                .uri(URI.create("https://accounts.spotify.com/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code=" + code[0].substring(5) + "&redirect_uri=http://localhost:8080"))
                .build();

        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("response:" + response.body());
            System.out.println("---SUCCESS---");
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return Status.SUCCEED;
    }
}
