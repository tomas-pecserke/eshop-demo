package sk.garwan.pecserke.eshop.order.model;

import sk.garwan.pecserke.eshop.product.model.ProductDto;

public class OrderItemDto {
    private final long id;
    private final ProductDto product;
    private final int count;
    private final double priceAtTimeOfPurchase;

    public OrderItemDto(long id, ProductDto product, int count, double priceAtTimeOfPurchase) {
        this.id = id;
        this.product = product;
        this.count = count;
        this.priceAtTimeOfPurchase = priceAtTimeOfPurchase;
    }

    public long getId() {
        return id;
    }

    public ProductDto getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public double getPriceAtTimeOfPurchase() {
        return priceAtTimeOfPurchase;
    }
}
