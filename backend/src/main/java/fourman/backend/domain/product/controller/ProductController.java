package fourman.backend.domain.product.controller;

import fourman.backend.domain.product.controller.dto.ImageResourceResponse;
import fourman.backend.domain.product.controller.dto.ProductListResponse;
import fourman.backend.domain.product.controller.dto.ProductRequest;
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
@CrossOrigin(origins = "http://localhost:8887", allowedHeaders = "*")
public class ProductController {

    final ProductService productService;

    @PostMapping(value = "/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public void productRegister(@RequestPart(value = "imageFileList") List<MultipartFile> imageFileList,
                                @RequestPart(value = "productInfo") ProductRequest productRequest) {
        log.info("productRegister() ");

        productService.register(imageFileList, productRequest);
    }

    @GetMapping("/list")
    public List<ProductListResponse> productList() {
        log.info("productList()");

        return productService.list();
    }

    @GetMapping("/imageList")
    public List<ImageResourceResponse> readProductImageResource() {

        log.info("readProuductImageResource(): ");

        return productService.loadProductImage();
    }
}
