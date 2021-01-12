package advisor;

import advisor.model.Representable;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static advisor.CommandType.*;

public class MusicAdvisor {
    private static final Integer DEFAULT_PAGE_SIZE = 5;
    private MusicServiceClient client;
    private Scanner scanner = new Scanner(System.in);
    private Integer pageSize;

    public MusicAdvisor() {
        client = new SpotifyClient();
        pageSize = DEFAULT_PAGE_SIZE;
    }

    public MusicAdvisor(String authHost, String apiHost, Integer pageSize) {
        client = new SpotifyClient(authHost, apiHost);
        this.pageSize = pageSize == null || pageSize < 1 ? DEFAULT_PAGE_SIZE : pageSize;
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
        while (cmd.keySet().toArray()[0] != EXIT) {
            switch ((CommandType) cmd.keySet().toArray()[0]) {
                case FEATURED:
                    cmd = paginateList(client.getFeatured());
                    continue;
                case NEW:
                    cmd = paginateList(client.getNewReleases());
                    continue;
                case CATEGORIES:
                    cmd = paginateList(client.getCategories());
                    continue;
                case PLAYLISTS:
                    String param = (String) cmd.values().toArray()[0];
                    cmd = paginateList(client.getPlaylists(param));
                    continue;
            }
            cmd = getNextCommand();
        }
    }

    private Map<CommandType, String> getNextCommand() {
        String line = scanner.nextLine();
        String[] cmd = line.split(" ");
        return Map.of(CommandType.fromString(cmd[0]), cmd.length > 1 ? line.substring(cmd[0].length() + 1) : "");
    }

    private <T extends Representable> Map<CommandType, String> paginateList(List<T> list) {
        Page<? extends Representable> page = new Page<>(list, pageSize);
        print(page.getPageData());
        print(page);

        Map<CommandType, String> cmd = getNextCommand();
        CommandType command = (CommandType) cmd.keySet().toArray()[0];
        while (command.equals(NEXT_PAGE) || command.equals(PREV_PAGE)) {
            switch (command) {
                case NEXT_PAGE:
                    if (!page.hasNext()) {
                        System.out.println("No more pages.");
                        break;
                    }
                    print(page.goNext().getPageData());
                    print(page);
                    break;
                case PREV_PAGE:
                    if (!page.hasPrev()) {
                        System.out.println("No more pages.");
                        break;
                    }
                    print(page.goPrev().getPageData());
                    print(page);
                    break;
                default:
                    return cmd;
            }
            cmd = getNextCommand();
            command = (CommandType) cmd.keySet().toArray()[0];
        }
        return cmd;
    }

    private void print(List<? extends Representable> resp) {
        for (Representable r : resp) {
            System.out.println(r.represent());
        }
    }

    private void print(Representable r) {
        System.out.println(r.represent());
    }
}
