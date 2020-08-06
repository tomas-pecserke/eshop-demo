package sk.garwan.pecserke.eshop.order.model;

import sk.garwan.pecserke.eshop.identity.model.UserDto;

import java.time.OffsetDateTime;
import java.util.List;

public class OrderDto {
    private final long id;
    private final UserDto user;
    private final List<OrderItemDto> items;
    private final OffsetDateTime createdAt;
    private final double totalPrice;

    public OrderDto(long id, UserDto user, List<OrderItemDto> items, OffsetDateTime createdAt, double totalPrice) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
    }

    public long getId() {
        return id;
    }

    public UserDto getUser() {
        return user;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
