package advisor;

public class Authentication {
    private String accessToken;
    private String refreshToken;

    public Authentication(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
