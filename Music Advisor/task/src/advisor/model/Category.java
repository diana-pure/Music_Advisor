package advisor.model;

public class Category implements Representable {
    private String id;
    private String name;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String represent() {
        return name;
    }
}
