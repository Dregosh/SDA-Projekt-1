package view;

import model.Order;
import model.OrderStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderMenuStateTest {
    private static OrderMenuState orderMenuState;

    @Test
    void shouldAssignValuesToOrderFields() throws IOException {
        //given
        System.setIn(Files.newInputStream(
                Path.of("src/main/resources/setOrderFields.txt")));
        orderMenuState = new OrderMenuState(null, null, null);
        Order order = new Order();
        //when
        orderMenuState.setOrderFields(order);
        //then
        assertEquals(LocalDate.of(2020, 9, 15), order.getOrderDate());
        assertNull(order.getCustomer());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(LocalDate.of(2020, 9, 22), order.getPaymentDate());
        Assertions.assertThat(order.getOrderItems()).hasSize(0);
    }
}
