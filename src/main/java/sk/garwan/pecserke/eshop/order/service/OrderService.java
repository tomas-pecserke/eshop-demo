package sk.garwan.pecserke.eshop.order.service;

import org.springframework.data.domain.Page;
import sk.garwan.pecserke.eshop.order.model.CreateOrderItemDto;
import sk.garwan.pecserke.eshop.order.model.OrderDto;

import java.util.List;

public interface OrderService {
    Page<OrderDto> getUserOrders(long userId, int pageNumber, int pageSize);
    Page<OrderDto> getOrders(int pageNumber, int pageSize);
    OrderDto createOrder(long userId, List<CreateOrderItemDto> items);
}
