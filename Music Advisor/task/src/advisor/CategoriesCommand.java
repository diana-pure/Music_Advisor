package advisor;

public class CategoriesCommand implements Command {
    @Override
    public void run() {
        System.out.println("---CATEGORIES---");
        System.out.println("Top Lists");
        System.out.println("Pop");
        System.out.println("Mood");
        System.out.println("Latin");
    }
}
