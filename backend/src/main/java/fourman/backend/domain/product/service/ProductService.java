package fourman.backend.domain.product.service;

import fourman.backend.domain.product.controller.dto.ImageResourceResponse;
import fourman.backend.domain.product.controller.dto.ProductListResponse;
import fourman.backend.domain.product.controller.dto.ProductRequest;
import fourman.backend.domain.product.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    void register(List<MultipartFile> imageFileList, ProductRequest productRequest);
    List<ProductListResponse> list();
    List<ImageResourceResponse> loadProductImage();
}
