package advisor;

import advisor.model.Category;

import java.util.List;

public interface MusicServiceClient {
    void authenticate();
    List<Category> getCategories();
    void getPlaylists(Category category);
    void getFeatured();
    void getNew();
}
