package advisor.command;

public class Exit implements Command {
    @Override
    public Status run() {
        System.out.println("---GOODBYE!---");
        return Status.SUCCEED;
    }
}
