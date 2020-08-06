package sk.garwan.pecserke.eshop.order.model;

import javax.validation.constraints.PositiveOrZero;

public class CreateOrderItemDto {
    private long product;

    @PositiveOrZero
    private int count;

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
