package fourman.backend.domain.product.service;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.repository.CafeRepository;
import fourman.backend.domain.product.controller.requestForm.EditProductRequestForm;
import fourman.backend.domain.product.controller.responseForm.AllProductResponseForm;
import fourman.backend.domain.product.controller.responseForm.ImageResourceResponseForm;
import fourman.backend.domain.product.controller.responseForm.ProductListResponseForm;
import fourman.backend.domain.product.controller.requestForm.ProductRequestForm;
import fourman.backend.domain.product.entity.ImageResource;
import fourman.backend.domain.product.entity.Product;
import fourman.backend.domain.product.repository.ImageResourceRepository;
import fourman.backend.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final private ProductRepository productRepository;
    final private ImageResourceRepository imageResourceRepository;
    final private CafeRepository cafeRepository;

    @Transactional
    @Override
    public void register(List<MultipartFile> imageFileList, ProductRequestForm productRequestForm) {

        List<ImageResource> imageResourceList = new ArrayList<>();

        final String fixedStringPath = "../FourMan-Front/src/assets/product/uploadImgs/";

        Product product = new Product();
        Optional<Cafe> maybeCafe = cafeRepository.findById(productRequestForm.getCafeId());
        Cafe cafe = maybeCafe.get();

        product.setProductName(productRequestForm.getProductName());
        product.setPrice(productRequestForm.getPrice());
        product.setDrinkType(productRequestForm.getDrinkType());
        product.setCafe(cafe);

        try{
            for(MultipartFile multipartFile: imageFileList) {
                log.info("requestFileUploadWithText() - filename: " + multipartFile.getOriginalFilename());

                String fullPath = fixedStringPath + multipartFile.getOriginalFilename();

                FileOutputStream writer = new FileOutputStream(fullPath);

                writer.write(multipartFile.getBytes());
                writer.close();

                ImageResource imageResource = new ImageResource(multipartFile.getOriginalFilename());
                imageResourceList.add(imageResource);
                product.setImageResource(imageResource);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        productRepository.save(product);

        imageResourceRepository.saveAll(imageResourceList);

    }

    @Override
    public List<AllProductResponseForm> all(Long cafeId) {
        Optional<Cafe> maybeCafe = cafeRepository.findById(cafeId);
        Cafe cafe = maybeCafe.get();
        List<Product> productList = productRepository.findAllByCafe(cafe);
        List<AllProductResponseForm> allProductList = new ArrayList<>();

        for (Product product: productList) {
            List<ImageResource> imageResourceList = imageResourceRepository.findImagePathListByProductId(product.getProductId());

            allProductList.add(new AllProductResponseForm(
                    product.getProductId(), product.getProductName(), product.getDrinkType(), product.getPrice(),
                    1, product.getPrice(), imageResourceList, cafeId));
        }

        return allProductList;
    }

    @Transactional
    @Override
    public Product editProductWithImage(List<MultipartFile> editImageFileList, EditProductRequestForm editProductRequestForm) {

        Long productId = editProductRequestForm.getProductId();
        log.info("productId: " + productId);

        Optional<Product> maybeProduct = productRepository.findProductById(productId);
        List<ImageResource> imageResourceList = imageResourceRepository.findImagePathListByProductId(productId);

        ImageResource imageResource = imageResourceList.get(0);

        if(maybeProduct.isEmpty()) {
            System.out.println("Product 정보를 찾지 못했습니다: " + productId);
            return null;
        }

        final String fixedStringPath = "../FourMan-Front/src/assets/product/uploadImgs/";

        Product product = maybeProduct.get();

        product.setProductName(editProductRequestForm.getProductName());
        product.setPrice(editProductRequestForm.getPrice());
        product.setDrinkType(editProductRequestForm.getDrinkType());

        try {
            for(MultipartFile multipartFile: editImageFileList) {
                String fullPath = fixedStringPath + multipartFile.getOriginalFilename();

                FileOutputStream writer = new FileOutputStream(fullPath);

                writer.write(multipartFile.getBytes());
                writer.close();

                imageResource.setImageResourcePath(multipartFile.getOriginalFilename());
                imageResourceList.set(0, imageResource);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        productRepository.save(product);

        imageResourceRepository.saveAll(imageResourceList);

        return product;

    }

    @Transactional
    @Override
    public Product editProductWithoutImage(EditProductRequestForm editProductRequestForm) {

        Long productId = editProductRequestForm.getProductId();
        log.info("productId: " + productId);

        Optional<Product> maybeProduct = productRepository.findProductById(productId);

        if(maybeProduct.isEmpty()) {
            System.out.println("Product 정보를 찾지 못했습니다: " + productId);
            return null;
        }

        Product product = maybeProduct.get();

        product.setProductName(editProductRequestForm.getProductName());
        product.setPrice(editProductRequestForm.getPrice());
        product.setDrinkType(editProductRequestForm.getDrinkType());

        productRepository.save(product);

        return product;
    }

    @Override
    public void remove(Long productId) {
        productRepository.deleteById(productId);
    }

}
