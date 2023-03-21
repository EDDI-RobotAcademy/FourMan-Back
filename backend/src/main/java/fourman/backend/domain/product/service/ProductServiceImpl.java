package fourman.backend.domain.product.service;

import fourman.backend.domain.product.controller.request.ProductRequest;
import fourman.backend.domain.product.entity.Product;
import fourman.backend.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final private ProductRepository productRepository;

    

    @Override
    public List<Product> list() {
        List<Product> productList = productRepository.findAll();

        return productList;
    }
}
