package advisor;

public class PlaylistsCommand implements Command {
    private final String playlists;

    public PlaylistsCommand(String playlists) {
        this.playlists = playlists;
    }

    @Override
    public void run() {
        System.out.printf("---%s PLAYLISTS---\n", playlists.toUpperCase());
        System.out.println("Walk Like A Badass  ");
        System.out.println("Rage Beats  ");
        System.out.println("Arab Mood Booster  ");
        System.out.println("Sunday Stroll");
    }
}
