package advisor;

import advisor.model.Representable;

import java.util.List;
import java.util.stream.Collectors;

public class Page<T> implements Representable {
    private List<T> list;
    private Integer pageNum;
    private Integer pageSize;
    private Integer total;

    public Page(List<T> list, Integer pageSize) {
        this.list = list;
        this.pageNum = 1;
        this.pageSize = pageSize;
        total = (int) Math.ceil(list.size() / (float) pageSize);
    }

    public boolean hasNext() {
        return pageNum < total;
    }

    public boolean hasPrev() {
        return pageNum > 1;
    }
    public Page<T> goNext() {
        pageNum = pageNum < total ? pageNum + 1 : pageNum;
        return this;
    }

    public Page<T> goPrev() {
        pageNum = pageNum > 1 ? pageNum - 1 : 1;
        return this;
    }

    public List<T> getPageData() {
        return list.stream().skip((pageNum - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }

    @Override
    public String represent() {
        return "---PAGE " + pageNum + " OF " + total + "---";
    }
}
