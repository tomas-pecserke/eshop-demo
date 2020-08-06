package sk.garwan.pecserke.eshop.product.mapper;

import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.DtoMapper;
import sk.garwan.pecserke.eshop.product.model.ImageDto;
import sk.garwan.pecserke.eshop.product.model.ProductDetailDto;
import sk.garwan.pecserke.eshop.product.persistance.AnimalCategory;
import sk.garwan.pecserke.eshop.product.persistance.Image;
import sk.garwan.pecserke.eshop.product.persistance.Product;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProductDetailMapper implements DtoMapper<Product, ProductDetailDto> {
    private final DtoMapper<Image, ImageDto> imageMapper;

    public ProductDetailMapper(DtoMapper<Image, ImageDto> imageMapper) {
        this.imageMapper = imageMapper;
    }

    @Override
    public ProductDetailDto map(Product product) {
        return new ProductDetailDto(
            Objects.requireNonNull(product.getId()),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getCategories().stream().map(AnimalCategory::getName).collect(Collectors.toSet()),
            imageMapper.mapList(product.getGallery())
        );
    }
}
