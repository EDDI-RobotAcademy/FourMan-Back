package fourman.backend.domain.product.service;

import fourman.backend.domain.product.controller.requestForm.EditProductRequestForm;
import fourman.backend.domain.product.controller.responseForm.AllProductResponseForm;
import fourman.backend.domain.product.controller.responseForm.ImageResourceResponseForm;
import fourman.backend.domain.product.controller.responseForm.ProductListResponseForm;
import fourman.backend.domain.product.controller.requestForm.ProductRequestForm;
import fourman.backend.domain.product.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    void register(List<MultipartFile> imageFileList, ProductRequestForm productRequestForm);
    List<ProductListResponseForm> list();
    List<ImageResourceResponseForm> loadProductImage();
    List<AllProductResponseForm> all();
    Product editProductWithImage(List<MultipartFile> editImageFileList, EditProductRequestForm editProductRequestForm);
    Product editProductWithoutImage(EditProductRequestForm editProductRequestForm);
    void remove(Long productId);
}
