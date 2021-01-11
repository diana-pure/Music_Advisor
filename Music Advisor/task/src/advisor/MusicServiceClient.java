package advisor;

import advisor.model.Album;
import advisor.model.Category;
import advisor.model.Playlist;

import java.util.List;

public interface MusicServiceClient {
    void authenticate();
    List<Category> getCategories();
    List<Playlist> getPlaylists(String categoryName);
    List<Playlist> getFeatured();
    List<Album> getNewReleases();
}
