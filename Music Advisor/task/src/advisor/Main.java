package advisor;

public class Main {
    public static void main(String[] args) {
        MusicAdvisor musicAdvisor = isMusicServiceHostCustomized(args) ? new MusicAdvisor(args[1]) : new MusicAdvisor();
        if (!musicAdvisor.authenticate()) {
            return;
        }
        musicAdvisor.getAdvice();
    }

    private static boolean isMusicServiceHostCustomized(String[] args) {
        return args.length > 0 && "-access".equals(args[0]);
    }
}
