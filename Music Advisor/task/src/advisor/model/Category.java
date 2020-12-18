package advisor.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class Category implements Representable {
    private String id;
    private String name;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Category> fromString(String plain) {
        JsonObject jo = JsonParser.parseString(plain).getAsJsonObject();
        JsonArray ja = jo.get("categories").getAsJsonObject().get("items").getAsJsonArray();
        List<Category> categories = new ArrayList<>(ja.size());
        for (JsonElement e : ja) {
            categories.add(new Category(e.getAsJsonObject().get("id").getAsString(), e.getAsJsonObject().get("name").getAsString()));
        }
        return categories;
    }

    @Override
    public String represent() {
        return name;
    }
}
