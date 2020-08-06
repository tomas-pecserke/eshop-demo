package sk.garwan.pecserke.eshop.product.persistance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByPriceBetweenAndNameStartsWith(
        Pageable pageable,
        double minPrice,
        double maxPrice,
        String nameStartsWith
    );

    Optional<Product> findByName(String name);
}
