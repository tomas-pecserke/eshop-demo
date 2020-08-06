package sk.garwan.pecserke.eshop.order.persistance;

import org.junit.jupiter.api.Test;
import sk.garwan.pecserke.eshop.product.persistance.Product;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {
    @Test
    void recalculateTotalPrice() {
        var order = new Order();
        assertThat(order.getTotalPrice()).isEqualTo(0);

        var product = new Product();
        product.setPrice(12.3);
        order.addItem(new OrderItem(product, 4));
        assertThat(order.getTotalPrice()).isEqualTo(12.3 * 4);

        var product2 = new Product();
        product2.setPrice(5.6);
        order.addItem(new OrderItem(product2, 7));
        assertThat(order.getTotalPrice()).isEqualTo(12.3 * 4 + 5.6 * 7);
    }
}
