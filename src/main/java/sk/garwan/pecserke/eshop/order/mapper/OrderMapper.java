package sk.garwan.pecserke.eshop.order.mapper;

import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.DtoMapper;
import sk.garwan.pecserke.eshop.identity.model.UserDto;
import sk.garwan.pecserke.eshop.identity.persistance.User;
import sk.garwan.pecserke.eshop.order.model.OrderDto;
import sk.garwan.pecserke.eshop.order.model.OrderItemDto;
import sk.garwan.pecserke.eshop.order.persistance.Order;
import sk.garwan.pecserke.eshop.order.persistance.OrderItem;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Component
public class OrderMapper implements DtoMapper<Order, OrderDto> {
    private final DtoMapper<User, UserDto> userMapper;
    private final DtoMapper<OrderItem, OrderItemDto> orderItemMapper;

    public OrderMapper(DtoMapper<User, UserDto> userMapper, DtoMapper<OrderItem, OrderItemDto> orderItemMapper) {
        this.userMapper = userMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderDto map(Order order) {
        return new OrderDto(
            requireNonNull(order.getId()),
            userMapper.map(requireNonNull(order.getUser())),
            orderItemMapper.mapList(order.getItems()),
            order.getCreatedAt(),
            order.getTotalPrice()
        );
    }
}
