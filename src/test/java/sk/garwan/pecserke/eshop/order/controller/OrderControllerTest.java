package sk.garwan.pecserke.eshop.order.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.test.web.servlet.MockMvc;
import sk.garwan.pecserke.eshop.identity.persistance.UserRepository;
import sk.garwan.pecserke.eshop.identity.service.UserService;
import sk.garwan.pecserke.eshop.order.persistance.Order;
import sk.garwan.pecserke.eshop.order.persistance.OrderItem;
import sk.garwan.pecserke.eshop.order.persistance.OrderRepository;
import sk.garwan.pecserke.eshop.product.model.ImageDto;
import sk.garwan.pecserke.eshop.product.persistance.Product;
import sk.garwan.pecserke.eshop.product.persistance.ProductRepository;
import sk.garwan.pecserke.eshop.product.service.ProductService;

import javax.transaction.Transactional;
import java.util.*;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Test
    void create() throws Exception {
        var user = userService.createUser(
            "test_user",
            "test_user@example.com",
            false,
            "test_user"
        );
        var product = productService.createProduct(
            "Test product",
            "Test product description",
            11.1,
            Collections.singleton("other"),
            Collections.emptyList()
        );

        mvc
            .perform(
                post("/orders")
                    .with(user("test_user").password("test_user"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"items\":[{\"product\":" + product.getId() + ",\"count\":2}]}")
            )
            .andDo(log())
            .andExpect(status().isCreated());

        var order = orderRepository
            .findAllByUserId(PageRequest.of(0, 1, Sort.Direction.DESC, "createdAt"), user.getId())
            .get()
            .findFirst();

        assertThat(order.isPresent()).isTrue();
        assertThat(order.get().getTotalPrice()).isEqualTo(2 * product.getPrice());
        assertThat(order.get().getItems().size()).isEqualTo(1);
        assertThat(order.get().getItems().get(0).getCount()).isEqualTo(2);
        assertThat(requireNonNull(order.get().getItems().get(0).getProduct()).getId()).isEqualTo(product.getId());
    }

    @Test
    @Transactional
    void my() throws Exception {
        var user = userRepository.getOne(userService.createUser(
            "test_user_2",
            "test_user_2@example.com",
            false,
            "test_user_2"
        ).getId());

        var product1 = createProduct(
            "Test product 1",
            "Test product description 1",
            11.1,
            new HashSet<>(Arrays.asList("cats", "other")),
            Collections.emptyList()
        );
        var product2 = createProduct(
            "Test product 2",
            "Test product description 2",
            14.2,
            new HashSet<>(Arrays.asList("dogs", "other")),
            Collections.emptyList()
        );

        var order1 = new Order();
        order1.setUser(user);
        order1.addItem(new OrderItem(product1, 2));
        order1.addItem(new OrderItem(product2, 3));
        orderRepository.save(order1);

        var order2 = new Order();
        order2.setUser(user);
        order2.addItem(new OrderItem(product1, 1));
        orderRepository.saveAndFlush(order2);

        mvc
            .perform(
                get("/orders/my")
                    .with(user("test_user_2").password("test_user_2"))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(log())
            .andExpect(status().isOk())
            .andExpect(jsonPath(".totalElements").value(2))
            .andExpect(jsonPath(".content[0]").isArray())
            .andExpect(jsonPath(".content[0].id").value(order2.getId().intValue()))
            .andExpect(jsonPath(".content[1].id").value(order1.getId().intValue()));
    }

    @Test
    void myNotAuthenticated() throws Exception {
        mvc
            .perform(get("/orders/my").accept(MediaType.APPLICATION_JSON))
            .andDo(log())
            .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void list() throws Exception {
        var user = userRepository.getOne(userService.createUser(
            "test_user_3",
            "test_user_3@example.com",
            false,
            "test_user_3"
        ).getId());

        var product = createProduct(
            "Test product 3",
            "Test product description 3",
            11.1,
            new HashSet<>(Collections.singletonList("other")),
            Collections.emptyList()
        );

        var order = new Order();
        order.setUser(user);
        order.addItem(new OrderItem(product, 5));
        orderRepository.deleteAll();
        orderRepository.saveAndFlush(order);

        mvc
            .perform(
                get("/orders")
                    .with(user("test_admin").password("test_admin").roles("ADMIN"))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(log())
            .andExpect(status().isOk())
            .andExpect(jsonPath(".totalElements").value(1))
            .andExpect(jsonPath(".content[0]").isArray())
            .andExpect(jsonPath(".content[0].id").value(order.getId().intValue()))
            .andExpect(jsonPath(".content[0].user.id").value(user.getId().intValue()));
    }

    @Test
    void listNotAdmin() throws Exception {
        mvc
            .perform(
                get("/orders")
                    .with(user("test_user").password("test_user"))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(log())
            .andExpect(status().isForbidden());
    }

    @Test
    void listNotAuthenticated() throws Exception {
        mvc
            .perform(get("/orders").accept(MediaType.APPLICATION_JSON))
            .andDo(log())
            .andExpect(status().isUnauthorized());
    }

    private Product createProduct(
        String name,
        @Nullable String description,
        double price,
        Set<String> categories,
        List<ImageDto> gallery
    ) {
        var product = productService.createProduct(name, description, price, categories, gallery);

        return productRepository.getOne(product.getId());
    }
}
