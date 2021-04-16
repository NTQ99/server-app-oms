package ntq.uet.server.models;

import java.util.List;

public class PageResponseModel<T> {

    public static class Meta {
        private int page;
        private int pages;
        private int perpage;
        private long total;
        private String sort = "asc";
        private String field = "orderCode";
        
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
        public Meta() {
        }
        public Meta(int page, int pages, int perpage, long total) {
            this.setPage(page);;
            this.setPages(pages);;
            this.setPerpage(perpage);
            this.setTotal(total);
        }
    }
    private Meta meta;
    private List<T> data;

    public PageResponseModel(){}

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public PageResponseModel(List<T> data, int currentPage, int totalPages, int perPage, long totalItems) {
        Meta meta = new Meta(currentPage + 1, totalPages, perPage, totalItems);
        this.setData(data);
        this.setMeta(meta);
    };
    
}
