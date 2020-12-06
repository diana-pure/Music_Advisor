package advisor;

public enum CommandType {
    AUTH("auth"),
    FEATURED("featured"),
    NEW("new"),
    CATEGORIES("categories"),
    PLAYLISTS("playlists"),
    EXIT("exit");

    private final String name;

    CommandType(String name) {
        this.name = name;
    }

    public static CommandType fromString(String s) {
        switch (s) {
            case "auth": return AUTH;
            case "featured": return FEATURED;
            case "new": return NEW;
            case "categories": return CATEGORIES;
            case "playlists": return PLAYLISTS;
            default: return EXIT;
        }
    }
}
