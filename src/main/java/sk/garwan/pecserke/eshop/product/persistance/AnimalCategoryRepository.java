package sk.garwan.pecserke.eshop.product.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalCategoryRepository extends JpaRepository<AnimalCategory, Long> {
    Optional<AnimalCategory> findByName(String name);
    AnimalCategory findOneByName(String name);
}
