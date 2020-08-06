package sk.garwan.pecserke.eshop.product.model;

import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

public class ProductDetailDto extends ProductDto {
    @Nullable
    private final String description;
    private final List<ImageDto> gallery;

    public ProductDetailDto(
        long id,
        String name,
        @Nullable String description,
        double price,
        Set<String> animalCategories,
        List<ImageDto> gallery
    ) {
        super(id, name, price, animalCategories);
        this.description = description;
        this.gallery = gallery;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public List<ImageDto> getGallery() {
        return gallery;
    }
}
