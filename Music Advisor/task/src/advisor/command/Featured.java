package advisor.command;

public class Featured implements Command {

    @Override
    public Status run() {
        System.out.println("---FEATURED---");
        System.out.println("Mellow Morning");
        System.out.println("Wake Up and Smell the Coffee");
        System.out.println("Monday Motivation");
        System.out.println("Songs to Sing in the Shower");
        return Status.SUCCEED;
    }
}
