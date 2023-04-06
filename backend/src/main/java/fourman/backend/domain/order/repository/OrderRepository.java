package fourman.backend.domain.order.repository;

import fourman.backend.domain.order.entity.OrderInfo;
import fourman.backend.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.keyvalue.repository.config.QueryCreatorType;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderInfo, Long> {

    @Query("select orderNo from OrderInfo")
    List<String> findFullOrderNumberByOrderNumber();
}
