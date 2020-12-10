package advisor.command;

public class Playlists implements Command {
    private final String playlists;

    public Playlists(String playlists) {
        this.playlists = playlists;
    }

    @Override
    public Status run() {
        System.out.printf("---%s PLAYLISTS---\n", playlists.toUpperCase());
        System.out.println("Walk Like A Badass  ");
        System.out.println("Rage Beats  ");
        System.out.println("Arab Mood Booster  ");
        System.out.println("Sunday Stroll");
        return Status.SUCCEED;
    }
}
