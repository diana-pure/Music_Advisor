package advisor.page;

import advisor.model.Representable;

import java.util.List;
import java.util.stream.Collectors;

public class Page<T extends Representable> implements Representable {
    private List<T> list;
    private Integer pageNum;
    private Integer pageSize;
    private Integer total;

    private Page(List<T> list, Integer pageNum, Integer pageSize, Integer total) {
        this.list = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }

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
    public Page<T> getNext() {
        if(!hasNext()) {
            throw new RuntimeException("Out of page bounds.");
        }
        return new Page<>(list, pageNum + 1, pageSize, total);
    }

    public Page<T> getPrev() {
        if(!hasPrev()) {
            throw new RuntimeException("Out of page bounds.");
        }
        return new Page<>(list, pageNum - 1, pageSize, total);
    }

    public List<T> getPageData() {
        return list.stream().skip((pageNum - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }

    @Override
    public String represent() {
        StringBuilder sb = new StringBuilder();
        for (Representable r : getPageData()) {
            sb.append(r.represent());
        }
        sb.append("---PAGE ").append(pageNum).append(" OF ").append(total).append("---");
        return sb.toString();
    }
}
