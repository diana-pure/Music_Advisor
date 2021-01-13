package advisor.page;

import advisor.model.Representable;

public interface PageTurner<T extends Representable> {
    Page<T> turn(Page<T> page);
}
