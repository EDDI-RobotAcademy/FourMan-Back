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

//    * AWS s3 사용을 위한 주석 처리
    void register(List<MultipartFile> imageFileList, ProductRequestForm productRequestForm);
//    void register(List<String> imageFileNameList, ProductRequestForm productRequestForm);
    List<AllProductResponseForm> all(Long cafeId);
//    * AWS s3 사용을 위한 주석 처리
    Product editProductWithImage(List<MultipartFile> editImageFileList, EditProductRequestForm editProductRequestForm);
//    Product editProductWithImage(EditProductRequestForm editProductRequestForm);
    Product editProductWithoutImage(EditProductRequestForm editProductRequestForm);
    void remove(Long productId);
}
