package advisor;

public class Main {
    public static void main(String[] args) {
        MusicAdvisor musicAdvisor = args.length > 0 && "-access".equals(args[0])
                ? new MusicAdvisor(args[1])
                : new MusicAdvisor();
        if (!musicAdvisor.authenticate()) {
            return;
        }
        musicAdvisor.getAdvice();
    }
}
