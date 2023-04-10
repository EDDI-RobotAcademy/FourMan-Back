package fourman.backend.domain.product.repository;

import fourman.backend.domain.product.entity.ImageResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageResourceRepository extends JpaRepository<ImageResource, Long> {

    @Query("select ir from ImageResource ir join ir.product p where p.productId = :id")
    List<ImageResource> findImagePathByProductId(Long id);
}
