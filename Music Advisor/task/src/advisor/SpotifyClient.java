package advisor;

import advisor.model.Category;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpotifyClient implements MusicServiceClient {
    private static final String DEFAULT_API_MEDIA_HOST = "https://api.spotify.com";
    private static final String DEFAULT_AUTH_MEDIA_HOST = "https://accounts.spotify.com";
    private static final String AUTH_PATH = "/authorize";
    private static final String API_TOKEN_PATH = "/api/token";
    private static final String CATEGORIES_PATH = "/v1/browse/categories";

    private static final String BASIC_AUTHORIZATION_HEADER = "Basic MWVhZDFiNDExN2FhNDNmM2FmZTIyMmZlNmRkZWQ5YzQ6ZjNkNTk1N2UyMjNmNGNiNGE3ZWVkZGZlZDRlMDc1ZGY=";
    private static final String CLIENT_ID = "1ead1b4117aa43f3afe222fe6dded9c4";

    private static final String CODE_NOT_FOUND_MESSAGE = "Authorization code not found. Try again.";
    private static final String CODE_RECEIVED_MESSAGE = "Got the code. Return back to your program.";

    private String apiMediaHost;
    private String authMediaHost;
    private String code;
    private Authentication authentication;
    private static HttpClient httpClient = HttpClient.newBuilder().build();

    public SpotifyClient() {
        authMediaHost = DEFAULT_AUTH_MEDIA_HOST;
        apiMediaHost = DEFAULT_API_MEDIA_HOST;
    }

    public SpotifyClient(String authMediaHost, String apiMediaHost) {
        this.authMediaHost = authMediaHost != null ? authMediaHost : DEFAULT_AUTH_MEDIA_HOST;
        this.apiMediaHost = apiMediaHost != null ? apiMediaHost : DEFAULT_API_MEDIA_HOST;
    }

    public void authenticate() {
        String userAcceptanceCode = getUserAcceptanceCode();
        System.out.println("making http request for access_token...");
        authentication = getAuthentication(userAcceptanceCode);
    }

    public List<Category> getCategories() {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + authentication.getAccessToken())
                .uri(URI.create(apiMediaHost + CATEGORIES_PATH))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("---CATEGORIES---");
            JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray ja = jo.get("categories").getAsJsonObject().get("items").getAsJsonArray();
            List<Category> cats = new ArrayList<>(ja.size());
            for(JsonElement e : ja){
                cats.add(new Category(e.getAsJsonObject().get("id").getAsString(), e.getAsJsonObject().get("name").getAsString()));
            }
            return cats;
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void getPlaylists(Category category) {

    }

    @Override
    public void getFeatured() {
    }

    @Override
    public void getNew() {

    }

    private String getUserAcceptanceCode() {
        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.createContext("/",
                    exchange -> {
                        code = exchange.getRequestURI().getQuery();
                        String clientMessage = "";
                        if (code == null || code.startsWith("error")) {
                            clientMessage = CODE_NOT_FOUND_MESSAGE;
                        } else if (code.startsWith("code=")) {
                            clientMessage = CODE_RECEIVED_MESSAGE;
                            System.out.println("code received: " + code);
                        }
                        exchange.sendResponseHeaders(200, clientMessage.length());
                        exchange.getResponseBody().write(clientMessage.getBytes());
                        exchange.getResponseBody().close();
                    }

            );
            server.start();
            System.out.println("use this link to request the access code:");
            System.out.println(authMediaHost + AUTH_PATH + "?client_id=" + CLIENT_ID + "&redirect_uri=http://localhost:8080&response_type=code");
            System.out.println("waiting for code...");

            while (code == null || !code.startsWith("code=")) {
                Thread.sleep(1);
            }
            server.stop(1);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return code.substring(5);
    }

    private Authentication getAuthentication(String code) {
        HttpRequest request = HttpRequest.newBuilder()
                .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", BASIC_AUTHORIZATION_HEADER)
                .uri(URI.create(authMediaHost + API_TOKEN_PATH))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code=" + code + "&redirect_uri=http://localhost:8080"))
                .build();

        String accessToken = null;
        String refreshToken = null;
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
            accessToken = jo.get("access_token").getAsString();
            refreshToken = jo.get("refresh_token").getAsString();
            System.out.println("response:" + response.body());
            System.out.println("---SUCCESS---");
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return new Authentication(accessToken, refreshToken);
    }
}
