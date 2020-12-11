package advisor.command;

import advisor.command.parameter.CommandParameter;

public class Exit implements Command {
    @Override
    public Status run(CommandParameter parameter) {
        System.out.println("---GOODBYE!---");
        return Status.SUCCEED;
    }
}
