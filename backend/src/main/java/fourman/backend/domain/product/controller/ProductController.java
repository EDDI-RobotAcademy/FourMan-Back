package fourman.backend.domain.product.controller;

import fourman.backend.domain.product.controller.request.ProductRequest;
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



    @GetMapping("/list")
    public List<Product> productList() {
        log.info("productList()");

        return productService.list();
    }
}
