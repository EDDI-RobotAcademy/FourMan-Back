package fourman.backend.domain.product.service;

import fourman.backend.domain.product.controller.responseForm.AllProductResponseForm;
import fourman.backend.domain.product.controller.responseForm.ImageResourceResponseForm;
import fourman.backend.domain.product.controller.responseForm.ProductCartResponseForm;
import fourman.backend.domain.product.controller.responseForm.ProductListResponseForm;
import fourman.backend.domain.product.controller.requestForm.ProductRequestForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    void register(List<MultipartFile> imageFileList, ProductRequestForm productRequestForm);
    List<ProductListResponseForm> list();
    List<ImageResourceResponseForm> loadProductImage();
    ProductCartResponseForm cart(Long productId);
    List<AllProductResponseForm> all();
}
