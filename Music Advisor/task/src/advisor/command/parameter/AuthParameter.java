package advisor.command.parameter;

public class AuthParameter extends CommandParameter {
    private final String host;

    public AuthParameter(String host) {
        this.host = host;
    }

    public String apply() {
        return host;
    }
}
