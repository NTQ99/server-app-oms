package ntq.uet.server.models;

import org.springframework.util.MultiValueMap;

public class PageRequestModel {

    public static class Pagination {
        private int page = 1;
        private int perpage = 10;

        public int getPage() {
            return page;
        }

        public int getPerpage() {
            return perpage;
        }

        public void setPerpage(int perpage) {
            this.perpage = perpage;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public Pagination() {
        }

        public Pagination(int page, int perpage) {
            this.page = page;
            this.perpage = perpage;
        }
    }

    public static class Query {
        private String status;
        private String generalSearch;

        public String getStatus() {
            return status;
        }

        public String getGeneralSearch() {
            return generalSearch;
        }

        public void setGeneralSearch(String generalSearch) {
            this.generalSearch = generalSearch;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Query() {
        }

        public Query(String status, String generalSearch) {
            this.status = status;
            this.generalSearch = generalSearch;
        }
    }

    public static class Sort {
        private String sort;
        private String field;

        public String getSort() {
            return sort;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public Sort() {
        }

        public Sort(String sort, String field) {
            this.sort = sort;
            this.field = field;
        }
    }

    private Pagination pagination;
    private Sort sort;
    private Query query;

    public Query getQuery() {
        return query;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public PageRequestModel() {
    }

    public PageRequestModel(Pagination pagination, Sort sort, Query query) {
        this.setPagination(pagination);
        this.setSort(sort);
        this.setQuery(query);
    }

    public static PageRequestModel getObject(MultiValueMap<String, String> object) {
        object.add("sort[sort]", "");
        object.add("sort[field]", "");
        object.add("query[status]", "");
        object.add("query[generalSearch]", "");
        Pagination pagination = new Pagination(Integer.parseInt(object.get("pagination[page]").get(0)),
                Integer.parseInt(object.get("pagination[perpage]").get(0)));
        Sort sort = new Sort(object.getFirst("sort[sort]"), object.getFirst("sort[field]"));
        Query query = new Query(object.getFirst("query[status]"), object.getFirst("query[generalSearch]"));
        return new PageRequestModel(pagination, sort, query);
    }

}
