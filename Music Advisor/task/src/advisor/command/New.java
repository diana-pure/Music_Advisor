package advisor.command;

import advisor.command.parameter.CommandParameter;

public class New implements Command {
    @Override
    public Status run(CommandParameter parameter) {
        System.out.println("---NEW RELEASES---");
        System.out.println("Mountains [Sia, Diplo, Labrinth]");
        System.out.println("Runaway [Lil Peep]");
        System.out.println("The Greatest Show [Panic! At The Disco]");
        System.out.println("All Out Life [Slipknot]");
        return Status.SUCCEED;
    }
}
