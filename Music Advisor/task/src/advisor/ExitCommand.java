package advisor;

public class ExitCommand implements Command {
    @Override
    public void run() {
        System.out.println("---GOODBYE!---");
    }
}
