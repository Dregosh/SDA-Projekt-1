package view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderItemMenuStateTest {

    @Test
    void shouldCalculateDiscountedPrice1() {
        //given
        double initialPrice = 1550;
        int discountPercent = 10;
        OrderItemMenuState orderItemMenuState = new OrderItemMenuState(null, null, null);
        //when
        double discountedPrice = orderItemMenuState.calculateDiscountedPrice(
                initialPrice, discountPercent);
        //then
        Assertions.assertThat(discountedPrice).isEqualTo(1395.0);
    }

    @Test
    void shouldCalculateDiscountedPrice2() {
        //given
        double initialPrice = 1000.33;
        int discountPercent = 17;
        OrderItemMenuState orderItemMenuState = new OrderItemMenuState(null, null, null);
        //when
        double discountedPrice = orderItemMenuState.calculateDiscountedPrice(
                initialPrice, discountPercent);
        //then
        Assertions.assertThat(discountedPrice).isEqualTo(830.2739);
    }
}