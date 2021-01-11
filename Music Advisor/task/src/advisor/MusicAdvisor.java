package advisor;

import advisor.model.Representable;

import java.util.List;
import java.util.Map;
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
        Map<CommandType, String> cmd = getNextCommand();
        CommandType command = (CommandType) cmd.keySet().toArray()[0];
        while (command != AUTH && command != EXIT) {
            System.out.println("Please, provide access for application.");
            cmd = getNextCommand();
            command = (CommandType) cmd.keySet().toArray()[0];
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
        Map<CommandType, String> cmd = getNextCommand();
        CommandType command = (CommandType) cmd.keySet().toArray()[0];
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
                    String param = (String) cmd.values().toArray()[0];
                    print(client.getPlaylists(param));
            }
            cmd = getNextCommand();
            command = (CommandType) cmd.keySet().toArray()[0];

        }
    }

    private Map<CommandType, String> getNextCommand() {
        String line = scanner.nextLine();
        String[] cmd = line.split(" ");
        return Map.of(CommandType.fromString(cmd[0]), cmd.length > 1 ? line.substring(cmd[0].length() + 1) : "");
    }

    private void print(List<? extends Representable> resp) {
        for (Representable r : resp) {
            System.out.println(r.represent());
        }
    }
}
