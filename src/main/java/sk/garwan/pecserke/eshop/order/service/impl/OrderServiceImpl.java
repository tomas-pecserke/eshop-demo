package sk.garwan.pecserke.eshop.order.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.DtoMapper;
import sk.garwan.pecserke.eshop.identity.persistance.UserRepository;
import sk.garwan.pecserke.eshop.order.model.CreateOrderItemDto;
import sk.garwan.pecserke.eshop.order.model.OrderDto;
import sk.garwan.pecserke.eshop.order.persistance.Order;
import sk.garwan.pecserke.eshop.order.persistance.OrderItem;
import sk.garwan.pecserke.eshop.order.persistance.OrderRepository;
import sk.garwan.pecserke.eshop.order.service.OrderService;
import sk.garwan.pecserke.eshop.product.persistance.ProductRepository;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final DtoMapper<Order, OrderDto> orderMapper;

    public OrderServiceImpl(
        OrderRepository orderRepository,
        ProductRepository productRepository,
        UserRepository userRepository,
        DtoMapper<Order, OrderDto> orderMapper
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public Page<OrderDto> getOrders(int pageNumber, int pageSize) {
        var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "createdAt");

        return orderRepository.findAll(pageRequest).map(orderMapper::map);
    }

    @Override
    @Transactional
    public Page<OrderDto> getUserOrders(long userId, int pageNumber, int pageSize) {
        var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "createdAt");

        return orderRepository.findAllByUserId(pageRequest, userId).map(orderMapper::map);
    }

    @Override
    @Transactional
    public OrderDto createOrder(long userId, List<CreateOrderItemDto> items) {
        var order = new Order();
        order.setUser(userRepository.getOne(userId));
        for (var dto : items) {
            var product = productRepository.getOne(dto.getProduct());

            var item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setCount(dto.getCount());
            item.setPriceAtTimeOfPurchase(product.getPrice());

            order.addItem(item);
        }
        order.setCreatedAt(OffsetDateTime.now());
        order = orderRepository.saveAndFlush(order);

        return orderMapper.map(order);
    }
}
