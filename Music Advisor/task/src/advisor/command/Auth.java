package advisor.command;

import advisor.command.parameter.AuthParameter;
import advisor.command.parameter.CommandParameter;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Auth implements Command {
    private String code;
    private boolean authenticationSucceed;

    @Override
    public Status run(CommandParameter parameter) {
        if(!(parameter instanceof AuthParameter)) {
            return Status.FAILED;
        }
        final String host = ((AuthParameter) parameter).apply();

        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.createContext("/",
                    exchange -> {
                        code = exchange.getRequestURI().getQuery();
                        String bmessage = "";
                        if (code == null || code.startsWith("error")) {
                            bmessage = "Authorization code not found. Try again.";
                            authenticationSucceed = false;
                        } else if (code.startsWith("code=")) {
                            bmessage = "Got the code. Return back to your program.";
                            System.out.println("code received: " + code);
                            authenticationSucceed = true;
                        }
                        exchange.sendResponseHeaders(200, bmessage.length());
                        exchange.getResponseBody().write(bmessage.getBytes());
                        exchange.getResponseBody().close();
                    }

            );
            server.start();
            System.out.println("use this link to request the access code:");
            System.out.println( host + "/authorize?client_id=1ead1b4117aa43f3afe222fe6dded9c4&redirect_uri=http://localhost:8080&response_type=code");
            System.out.println("waiting for code...");

            while (code == null || !code.startsWith("code=")) {
                Thread.sleep(1);
            }
            server.stop(1);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (!authenticationSucceed) {
            return Status.FAILED;
        }

        System.out.println("making http request for access_token...");

        HttpRequest request = HttpRequest.newBuilder()
                .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", "Basic MWVhZDFiNDExN2FhNDNmM2FmZTIyMmZlNmRkZWQ5YzQ6ZjNkNTk1N2UyMjNmNGNiNGE3ZWVkZGZlZDRlMDc1ZGY=")
                .uri(URI.create(host + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code=" + code.substring(5) + "&redirect_uri=http://localhost:8080"))
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
