package advisor;

import advisor.command.*;

import java.util.Collections;
import java.util.List;

public class MusicAdvisor {
    private static final String DEFAUTL_MUSIC_SERVICE_HOST = "https://accounts.spotify.com";
    private final String musicServiceHost;
    private boolean isAuthenticated = false;

    public MusicAdvisor() {
        musicServiceHost = DEFAUTL_MUSIC_SERVICE_HOST;
    }

    public MusicAdvisor(String musicServiceHost) {
        this.musicServiceHost = musicServiceHost;
    }

    public boolean authenticate() {
        Command command = CommandParser.parse();
        while (!(command instanceof Auth) && !(command instanceof Exit)) {
            System.out.println("Please, provide access for application.");
            command = CommandParser.parse();
        }
        if (command instanceof Auth) {
            return command.run().equals(Status.SUCCEED);
        }
        command.run();
        return false;
    }

    public List<String> getAdvice() {
        Command command = CommandParser.parse();
        while (!(command instanceof Exit)) {
            command.run();
            command = CommandParser.parse();
        }
        command.run();
        return Collections.emptyList();
    }
}
