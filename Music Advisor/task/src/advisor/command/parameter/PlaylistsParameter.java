package advisor.command.parameter;

public class PlaylistsParameter extends CommandParameter {
private final String name;

    public PlaylistsParameter(String name) {
        this.name = name;
    }

    public String apply() {
        return name;
    }
}
