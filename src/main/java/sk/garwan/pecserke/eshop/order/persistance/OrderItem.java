package sk.garwan.pecserke.eshop.order.persistance;

import org.springframework.lang.Nullable;
import sk.garwan.pecserke.eshop.product.persistance.Product;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class OrderItem {
    @Nullable
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    @NotNull
    private Order order;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    @NotNull
    private Product product;

    @Column(nullable = false)
    @PositiveOrZero
    private int count;

    @Column(nullable = false)
    @PositiveOrZero
    private double priceAtTimeOfPurchase;

    public OrderItem() {
    }

    public OrderItem(Product product, int count) {
        this.product = product;
        this.count = count;
        priceAtTimeOfPurchase = product.getPrice();
    }

    @Nullable
    public Long getId() {
        return id;
    }

    @Nullable
    public Order getOrder() {
        return order;
    }

    public void setOrder(@Nullable Order order) {
        var previous = this.order;
        this.order = order;
        if (previous != order && previous != null && previous.hasItem(this)) {
            previous.removeItem(this);
        }
    }

    @Nullable
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPriceAtTimeOfPurchase() {
        return priceAtTimeOfPurchase;
    }

    public void setPriceAtTimeOfPurchase(double priceAtTimeOfPurchase) {
        this.priceAtTimeOfPurchase = priceAtTimeOfPurchase;
    }

    public double getPriceForCount() {
        return priceAtTimeOfPurchase * count;
    }
}
