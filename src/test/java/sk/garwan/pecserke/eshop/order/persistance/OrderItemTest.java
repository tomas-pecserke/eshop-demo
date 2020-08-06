package sk.garwan.pecserke.eshop.order.persistance;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {
    @Test
    void getPriceForCount() {
        var item = new OrderItem();
        item.setPriceAtTimeOfPurchase(10.1);
        item.setCount(3);

        assertThat(item.getPriceForCount()).isEqualTo(10.1 * 3);
    }
}
