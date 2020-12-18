package advisor;

import advisor.model.Category;
import advisor.model.Representable;

import java.util.List;
import java.util.Scanner;

import static advisor.CommandType.AUTH;
import static advisor.CommandType.EXIT;

public class MusicAdvisor {
    private MusicServiceClient client;
    private Scanner scanner = new Scanner(System.in);

    public MusicAdvisor() {
        client = new SpotifyClient();
    }

    public MusicAdvisor(String authHost, String apiHost) {
        client = new SpotifyClient(authHost, apiHost);
    }

    public boolean authenticate() {
        CommandType command = getNextCommand();
        while (command != AUTH && command != EXIT) {
            System.out.println("Please, provide access for application.");
            command = getNextCommand();
        }

        switch (command) {
            case EXIT:
                return false;
            case AUTH:
                client.authenticate();
        }

        return true;
    }

    public void getAdvice() {
        CommandType command = getNextCommand();
        while (command != EXIT) {
            switch (command) {
                case FEATURED:
                    print(client.getFeatured());
                    break;
                case NEW:
                    print(client.getNewReleases());
                    break;
                case CATEGORIES:
                    print(client.getCategories());
                    break;
                case PLAYLISTS:
                    print(client.getPlaylists(new Category("","")));
            }
            command = getNextCommand();
        }
    }

    private CommandType getNextCommand() {
        return CommandType.fromString(scanner.next());
    }

    private void print(List<? extends Representable> resp) {
        for (Representable r : resp) {
            System.out.println(r.represent());
        }
    }
}
