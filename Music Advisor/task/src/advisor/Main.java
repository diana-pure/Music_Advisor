package advisor;

public class Main {
    public static void main(String[] args) {
        MusicAdvisor musicAdvisor = args.length > 0 ? getMusicAdvisorWithCustomisedHosts(args) : new MusicAdvisor();
        if (!musicAdvisor.authenticate()) {
            return;
        }
        musicAdvisor.getAdvice();
    }

    private static MusicAdvisor getMusicAdvisorWithCustomisedHosts(String[] args) {
        String accessHost = null;
        String resourceHost = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-access":
                    if (i + 1 < args.length - 1) {
                        accessHost = args[i + 1];
                        i++;
                    }
                    break;
                case "-resource":
                    if (i + 1 < args.length - 1) {
                        resourceHost = args[i + 1];
                        i++;
                    }
                    break;

            }
        }

        if (accessHost == null && resourceHost == null) {
            return new MusicAdvisor();
        }

        return new MusicAdvisor(accessHost, resourceHost);
    }
}
