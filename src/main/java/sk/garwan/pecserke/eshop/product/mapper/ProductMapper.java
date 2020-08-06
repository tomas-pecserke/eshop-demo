package sk.garwan.pecserke.eshop.product.mapper;

import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.product.model.ProductDto;
import sk.garwan.pecserke.eshop.product.persistance.AnimalCategory;
import sk.garwan.pecserke.eshop.product.persistance.Product;
import sk.garwan.pecserke.eshop.DtoMapper;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProductMapper implements DtoMapper<Product, ProductDto> {
    @Override
    public ProductDto map(Product product) {
        return new ProductDto(
            Objects.requireNonNull(product.getId()),
            product.getName(),
            product.getPrice(),
            product.getCategories().stream().map(AnimalCategory::getName).collect(Collectors.toSet())
        );
    }
}
