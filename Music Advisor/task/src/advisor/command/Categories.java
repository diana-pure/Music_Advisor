package advisor.command;

import advisor.command.parameter.CommandParameter;

public class Categories implements Command {
    @Override
    public Status run(CommandParameter parameter) {
        System.out.println("---CATEGORIES---");
        System.out.println("Top Lists");
        System.out.println("Pop");
        System.out.println("Mood");
        System.out.println("Latin");
        return Status.SUCCEED;
    }
}
