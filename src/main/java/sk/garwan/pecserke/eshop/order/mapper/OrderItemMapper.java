package sk.garwan.pecserke.eshop.order.mapper;

import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.DtoMapper;
import sk.garwan.pecserke.eshop.order.model.OrderItemDto;
import sk.garwan.pecserke.eshop.order.persistance.OrderItem;
import sk.garwan.pecserke.eshop.product.model.ProductDto;
import sk.garwan.pecserke.eshop.product.persistance.Product;

import java.util.Objects;

@Component
public class OrderItemMapper implements DtoMapper<OrderItem, OrderItemDto> {
    private final DtoMapper<Product, ProductDto> productMapper;

    public OrderItemMapper(DtoMapper<Product, ProductDto> productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public OrderItemDto map(OrderItem item) {
        return new OrderItemDto(
            Objects.requireNonNull(item.getId()),
            productMapper.map(Objects.requireNonNull(item.getProduct())),
            item.getCount(),
            item.getPriceAtTimeOfPurchase()
        );
    }
}
