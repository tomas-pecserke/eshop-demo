package sk.garwan.pecserke.eshop.product.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CreateProductDto {
    @NotBlank
    @Length(max = 255)
    private String name;

    @Nullable
    private String description;

    @PositiveOrZero
    private double price;

    @NotEmpty
    private Set<String> categories;

    private List<ImageDto> gallery;

    public CreateProductDto() {
        this("", null, 0, Collections.emptySet(), Collections.emptyList());
    }

    public CreateProductDto(String name, @Nullable String description, double price, Set<String> categories, List<ImageDto> gallery) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categories = categories;
        this.gallery = gallery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public List<ImageDto> getGallery() {
        return gallery;
    }

    public void setGallery(List<ImageDto> gallery) {
        this.gallery = gallery;
    }
}
