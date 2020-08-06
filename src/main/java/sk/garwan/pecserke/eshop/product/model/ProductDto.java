package sk.garwan.pecserke.eshop.product.model;

import java.util.Set;

public class ProductDto {
    private final long id;
    private final String name;
    private final double price;
    private final Set<String> categories;

    public ProductDto(long id, String name, double price, Set<String> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Set<String> getCategories() {
        return categories;
    }
}
