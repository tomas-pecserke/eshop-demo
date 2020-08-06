package sk.garwan.pecserke.eshop.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sk.garwan.pecserke.eshop.identity.UserProvider;
import sk.garwan.pecserke.eshop.order.model.CreateOrderDto;
import sk.garwan.pecserke.eshop.order.model.OrderDto;
import sk.garwan.pecserke.eshop.order.service.OrderService;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

import static java.util.Objects.requireNonNull;

@RestController
public class OrderController {
    private final OrderService orderService;
    private final UserProvider userProvider;
    private final int defaultPageSize;

    public OrderController(
        OrderService orderService,
        UserProvider userProvider,
        @Value("${eshop.pagination.defaultPageSize}") int defaultPageSize
    ) {
        this.orderService = orderService;
        this.userProvider = userProvider;
        this.defaultPageSize = defaultPageSize;
    }

    @GetMapping("/orders")
    @RolesAllowed("ADMIN")
    @Transactional
    public Page<OrderDto> list(
        @RequestParam(name = "page", defaultValue = "0") @PositiveOrZero int pageNumber,
        @RequestParam(name = "size", defaultValue = "0") @PositiveOrZero int pageSize
    ) {
        return orderService.getOrders(pageNumber, pageSize == 0 ? defaultPageSize : pageSize);
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed("USER")
    @Transactional
    public void create(@RequestBody @Valid CreateOrderDto createOrder) {
        var user = userProvider.get().orElseThrow(IllegalStateException::new);
        orderService.createOrder(requireNonNull(user.getId()), createOrder.getItems());
    }

    @GetMapping("/orders/my")
    @RolesAllowed("USER")
    @Transactional
    public Page<OrderDto> my(
        @RequestParam(name = "page", defaultValue = "0") @PositiveOrZero int pageNumber,
        @RequestParam(name = "size", defaultValue = "0") @PositiveOrZero int pageSize
    ) {
        var user = userProvider.get().orElseThrow(IllegalStateException::new);

        return orderService.getUserOrders(
            requireNonNull(user.getId()),
            pageNumber,
            pageSize == 0 ? defaultPageSize : pageSize
        );
    }
}
