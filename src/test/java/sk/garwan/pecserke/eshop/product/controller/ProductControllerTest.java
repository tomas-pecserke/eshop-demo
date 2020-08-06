package sk.garwan.pecserke.eshop.product.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sk.garwan.pecserke.eshop.product.persistance.ProductRepository;
import sk.garwan.pecserke.eshop.product.service.ProductService;

import javax.transaction.Transactional;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        productRepository.flush();
    }

    @Test
    @Transactional
    void list() throws Exception {
        productService.createProduct(
            "Test Product 1",
            null,
            12.3,
            Collections.singleton("other"),
            Collections.emptyList()
        );
        productService.createProduct(
            "Test Product 2",
            null,
            4.5,
            Collections.singleton("cats"),
            Collections.emptyList()
        );
        productService.createProduct(
            "Test Product 3",
            null,
            6.78,
            Collections.singleton("dogs"),
            Collections.emptyList()
        );

        mvc
            .perform(
                get("/products")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(log())
            .andExpect(status().isOk())
            .andExpect(jsonPath(".totalElements").value(3))
            .andExpect(jsonPath(".content[0].name").value("Test Product 1"))
            .andExpect(jsonPath(".content[0].price").value(12.3))
            .andExpect(jsonPath(".content[1].name").value("Test Product 2"))
            .andExpect(jsonPath(".content[1].price").value(4.5))
            .andExpect(jsonPath(".content[2].name").value("Test Product 3"))
            .andExpect(jsonPath(".content[2].price").value(6.78));
    }

    @Test
    @Transactional
    void detail() throws Exception {
        var product = productService.createProduct(
            "Test Product 1",
            "Test Product 1 Description",
            12.3,
            Collections.singleton("other"),
            Collections.emptyList()
        );

        mvc
            .perform(get("/products/" + product.getId()).accept(MediaType.APPLICATION_JSON))
            .andDo(log())
            .andExpect(status().isOk())
            .andExpect(jsonPath(".name").value("Test Product 1"))
            .andExpect(jsonPath(".description").value("Test Product 1 Description"))
            .andExpect(jsonPath(".price").value(12.3))
            .andExpect(jsonPath(".categories").isArray())
            .andExpect(jsonPath(".categories[0]").value("other"));
    }

    @Test
    @Transactional
    void detailNotExists() throws Exception {
        mvc
            .perform(get("/products/0").accept(MediaType.APPLICATION_JSON))
            .andDo(log())
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void create() throws Exception {
        mvc
            .perform(
                post("/products")
                    .with(user("test_admin").password("test_admin").roles("ADMIN"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"Created Product\",\"price\":123.4,\"categories\":[\"other\"]}")
            )
            .andDo(log())
            .andExpect(status().isCreated());

        var product = productRepository.findByName("Created Product").orElseThrow(IllegalStateException::new);

        assertThat(product.getDescription()).isNull();
        assertThat(product.getPrice()).isEqualTo(123.4);
        assertThat(product.getCategories().size()).isEqualTo(1);
        assertThat(product.getCategories().iterator().next().getName()).isEqualTo("other");
    }
}
