package fourman.backend.domain.product.service;

import fourman.backend.domain.product.controller.request.ProductRequest;
import fourman.backend.domain.product.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    List<Product> list();

}
