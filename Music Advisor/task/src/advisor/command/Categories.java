package advisor.command;

public class Categories implements Command {
    @Override
    public Status run() {
        System.out.println("---CATEGORIES---");
        System.out.println("Top Lists");
        System.out.println("Pop");
        System.out.println("Mood");
        System.out.println("Latin");
        return Status.SUCCEED;
    }
}
