package fourman.backend.domain.product.repository;

import fourman.backend.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p join fetch p.imageResourceList irl where p.productId = :id")
    Optional<Product> findProductById(Long id);
}
