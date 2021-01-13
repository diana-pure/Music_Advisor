package advisor.page;

import advisor.model.Representable;

public class PageTurnerHolder<T extends Representable> {
    private PageTurner<T> pageTurner;

    public PageTurnerHolder<T> hold(PageTurner<T> pageTurner) {
        this.pageTurner = pageTurner;
        return this;
    }

    public Page<T> turn(Page<T> page) {
        return pageTurner.turn(page);
    }
}
