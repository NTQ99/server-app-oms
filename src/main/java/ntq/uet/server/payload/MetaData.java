package ntq.uet.server.payload;

public class MetaData {
    private int page;
    private int pages;
    private int perpage;
    private long total;
    private String sort = "desc";
    private String field = "createdAt";

    public int getPage() {
        return page;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public MetaData() {
        this.setPage(1);
        this.setPerpage(10);
    }

    public MetaData(int page, int pages, int perpage, long total) {
        this.setPage(page);
        this.setPages(pages);
        this.setPerpage(perpage);
        this.setTotal(total);
    }
}
