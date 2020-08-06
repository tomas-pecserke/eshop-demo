package sk.garwan.pecserke.eshop.order.model;

import java.util.LinkedList;
import java.util.List;

public class CreateOrderDto {
    private List<CreateOrderItemDto> items;

    public CreateOrderDto() {
        items = new LinkedList<>();
    }

    public List<CreateOrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<CreateOrderItemDto> items) {
        this.items = items;
    }
}
