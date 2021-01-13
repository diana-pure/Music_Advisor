package advisor.page;

import advisor.model.Representable;

public class PageTurnerNext<T extends Representable> implements PageTurner<T> {
    private static final PageTurner pageTurner = new PageTurnerNext<>();

    private PageTurnerNext() {
    }

    public static PageTurner getInstance() {
        return pageTurner;
    }

    @Override
    public Page<T> turn(Page<T> page) {
        if (!page.hasNext()) {
            System.out.println("No more pages.");
            return page;
        }
        return page.getNext();
    }
}
