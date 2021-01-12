package advisor;

public enum CommandType {
    AUTH("auth"),
    FEATURED("featured"),
    NEW("new"),
    CATEGORIES("categories"),
    PLAYLISTS("playlists"),
    NEXT_PAGE("next"),
    PREV_PAGE("prev"),
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
            case "next": return NEXT_PAGE;
            case "prev": return PREV_PAGE;
            default:
                if(s.startsWith("playlists")) {
                    return PLAYLISTS;
                }
                return EXIT;
        }
    }
}
