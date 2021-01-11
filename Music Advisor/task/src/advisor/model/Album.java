package advisor.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Album implements Representable {
    private String name;
    private String artists;
    private String reference;

    public Album(String id, String name, String artists, String reference) {
        this.name = name;
        this.artists = artists;
        this.reference = reference;
    }

    public static List<Album> fromString(String plain) {
        JsonObject jo = JsonParser.parseString(plain).getAsJsonObject();
        JsonElement je = jo.get("albums");
        if(je == null) {
            return Collections.emptyList();
        }
        JsonArray ja = je.getAsJsonObject().get("items").getAsJsonArray();
        List<Album> albums = new ArrayList<>(ja.size());
        for (JsonElement e : ja) {
            JsonArray artists = e.getAsJsonObject().get("artists").getAsJsonArray();
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (JsonElement artist : artists) {
                String s = artist.getAsJsonObject().get("name").toString();
                s = s.substring(1, s.length() - 1);
                sb.append(s).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("]");
            String ref = e.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString();
            albums.add(new Album(
                    e.getAsJsonObject().get("id").getAsString(),
                    e.getAsJsonObject().get("name").getAsString(),
                    sb.toString(),
                    ref));
        }
        return albums;
    }

    @Override
    public String represent() {
        return name + "\n" + artists + "\n" + reference + "\n";
    }
}
