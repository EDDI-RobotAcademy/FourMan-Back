package fourman.backend.domain.cafeIntroduce.service.response;

import java.util.List;

public class CafeTop3ProductListResponse {
    private List<CafeTop3ProductResponse> topProducts;

    public CafeTop3ProductListResponse(List<CafeTop3ProductResponse> topProducts) {
        this.topProducts = topProducts;
    }

    public List<CafeTop3ProductResponse> getTopProducts() {
        return topProducts;
    }

    public void setTopProducts(List<CafeTop3ProductResponse> topProducts) {
        this.topProducts = topProducts;
    }
}
