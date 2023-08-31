package ntq.uet.server.common.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetaData {

    private int page = 1;
    private int pages;
    private int perpage = 10;
    private long total;
    private String sort = "desc";
    private String field = "createdAt";

    public MetaData(int page, int pages, int perpage, long total) {
        this.setPage(page);
        this.setPages(pages);
        this.setPerpage(perpage);
        this.setTotal(total);
    }
}
