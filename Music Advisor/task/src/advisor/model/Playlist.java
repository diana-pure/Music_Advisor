package advisor.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class Playlist implements Representable {
    private String name;
    private String reference;

    public Playlist(String name, String reference) {
        this.name = name;
        this.reference = reference;
    }

    public static List<Playlist> fromString(String plain) {
        JsonObject jo = JsonParser.parseString(plain).getAsJsonObject();
        JsonArray ja = jo.get("playlists").getAsJsonObject().get("items").getAsJsonArray();
        List<Playlist> playlists = new ArrayList<>(ja.size());
        for (JsonElement e : ja) {
            playlists.add(new Playlist(e.getAsJsonObject().get("id").getAsString(), e.getAsJsonObject().get("name").getAsString()));
        }
        return playlists;
    }

    @Override
    public String represent() {
        return null;
    }
}
