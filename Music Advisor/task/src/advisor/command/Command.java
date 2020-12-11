package advisor.command;

import advisor.command.parameter.CommandParameter;
import advisor.command.parameter.EmptyParameter;

public interface Command {
    default Status run() {
        return run(new EmptyParameter());
    }

    Status run(CommandParameter parameter);
}
