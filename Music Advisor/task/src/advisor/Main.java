package advisor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        do {
            switch (CommandType.fromString(s.next())) {
                case FEATURED:
                    new FeaturedCommand().run();
                    break;
                case NEW:
                    new NewCommand().run();
                    break;
                case CATEGORIES:
                    new CategoriesCommand().run();
                    break;
                case PLAYLISTS:
                    new PlaylistsCommand(s.next()).run();
                    break;
                case EXIT:
                    new ExitCommand().run();
                    return;
            }
        } while (true);
    }
}
