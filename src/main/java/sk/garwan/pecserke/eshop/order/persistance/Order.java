package sk.garwan.pecserke.eshop.order.persistance;

import org.springframework.lang.Nullable;
import sk.garwan.pecserke.eshop.identity.persistance.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user_order")
public class Order {
    @Nullable
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    @NotNull
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> items;

    @Column(nullable = false)
    @NotNull
    @PastOrPresent
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(nullable = false)
    @PositiveOrZero
    private double totalPrice;

    public Order() {
        items = new LinkedList<>();
    }

    @Nullable
    public Long getId() {
        return id;
    }

    @Nullable
    public User getUser() {
        return user;
    }

    public void setUser(@Nullable User user) {
        this.user = user;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean hasItem(OrderItem item) {
        return items.contains(item);
    }

    public void addItem(OrderItem item) {
        items.add(item);
        if (item.getOrder() != this) {
            item.setOrder(this);
        }
        recalculateTotalPrice();
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        if (item.getOrder() == this) {
            item.setOrder(null);
        }
        recalculateTotalPrice();
    }

    public void removeAllItems() {
        var items = new LinkedList<>(this.items);
        this.items.clear();
        items.forEach(item -> item.setOrder(null));
        recalculateTotalPrice();
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void recalculateTotalPrice() {
        totalPrice = items.stream().mapToDouble(OrderItem::getPriceForCount).sum();
    }
}
