package advisor.page;

import advisor.model.Representable;

public class PageTurnerPrev<T extends Representable> implements PageTurner<T> {
    private static final PageTurner pageTurner = new PageTurnerPrev<>();

    private PageTurnerPrev() {
    }

    public static PageTurner getInstance() {
        return pageTurner;
    }

    @Override
    public Page<T> turn(Page<T> page) {
        if (!page.hasPrev()) {
            System.out.println("No more pages.");
            return page;
        }
        return page.getPrev();
    }
}
