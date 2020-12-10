package advisor.command;

import java.util.Scanner;

public class CommandParser {
    private static Scanner s = new Scanner(System.in);

    public static Command parse() {
        switch (CommandType.fromString(s.next())) {
            case AUTH:
                return new Auth();
            case FEATURED:
                return new Featured();
            case NEW:
                return new New();
            case CATEGORIES:
                return new Categories();
            case PLAYLISTS:
                return new Playlists(s.next());
            default:
                return new Exit();
        }
    }
}
