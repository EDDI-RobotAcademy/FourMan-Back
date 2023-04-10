package fourman.backend.domain.product.controller;

import fourman.backend.domain.product.controller.requestForm.EditProductRequestForm;
import fourman.backend.domain.product.controller.responseForm.AllProductResponseForm;
import fourman.backend.domain.product.controller.responseForm.ImageResourceResponseForm;
import fourman.backend.domain.product.controller.responseForm.ProductListResponseForm;
import fourman.backend.domain.product.controller.requestForm.ProductRequestForm;
import fourman.backend.domain.product.entity.Product;
import fourman.backend.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public void productRegister(@RequestPart(value = "imageFileList") List<MultipartFile> imageFileList,
                                @RequestPart(value = "productInfo") ProductRequestForm productRequestForm) {
        log.info("productRegister()");

        productService.register(imageFileList, productRequestForm);
    }

    @GetMapping("/list")
    public List<ProductListResponseForm> productList() {
        log.info("productList()");

        return productService.list();
    }

    @GetMapping("/imageList")
    public List<ImageResourceResponseForm> readProductImageResource() {

        log.info("readProuductImageResource(): ");

        return productService.loadProductImage();
    }

    @GetMapping("/all")
    public List<AllProductResponseForm> allProductList () {
        log.info("allProductList()");

        return productService.all();
    }

    @PostMapping(value = "/editProductWithImage", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Product editProductWithImage(@RequestPart(value = "editedProductImage") List<MultipartFile> editImageFileList,
                                        @RequestPart(value = "editedProductInfo") EditProductRequestForm editProductRequestForm) {
        log.info("productModifyWithImage()");

        return productService.editProductWithImage(editImageFileList, editProductRequestForm);
    }

    @PostMapping(value = "/editProductWithoutImage", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Product editProductWithoutImage(@RequestPart(value = "editedProductInfo") EditProductRequestForm editProductRequestForm) {
        log.info("productModifyWithoutImage()");

        return productService.editProductWithoutImage(editProductRequestForm);
    }

}
