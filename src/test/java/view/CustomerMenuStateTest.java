package view;

import model.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMenuStateTest {
    private static ByteArrayInputStream mockInput;
    private CustomerMenuState customerMenuState;
    private static final boolean BLANK_INPUT_ALLOWED = true;
    private static final boolean BLANK_INPUT_NOT_ALLOWED = false;

    @Test
    void shouldAssignValuesToCustomerFields() throws IOException {
        //given
        System.setIn(Files.newInputStream(
                Path.of("src/main/resources/setCustomerFields.txt")));
        customerMenuState = new CustomerMenuState(null);
        Customer customer = new Customer();
        //when
        customerMenuState.setCustomerFields(customer);
        //then
        assertEquals("sampleLastName", customer.getLastName());
        assertEquals("sampleFirstName", customer.getFirstName());
        assertEquals("sampleStreet", customer.getAddressStreet());
        assertEquals("sampleCode", customer.getAddressPostalCode());
        assertEquals("sampleCity", customer.getAddressCity());
        Assertions.assertThat(customer.getOrders()).hasSize(0);
    }

    @Test
    void shouldReturnNullCustomer() {
        //given
        String input = "0\n";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        customerMenuState = new CustomerMenuState(null);
        //when
        Customer result = customerMenuState.addNewCustomerOption();
        //then
        Assertions.assertThat(result).isNull();
    }

    @Test
    void shouldReturnInput() {
        //given
        String input = "sampleAttributeValue";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        customerMenuState = new CustomerMenuState(null);
        //when
        String result = customerMenuState.defineAttribute(BLANK_INPUT_NOT_ALLOWED, input);
        //then
        Assertions.assertThat(result).isEqualTo(input);
    }

    @Test
    void shouldReturnBlankString() {
        //given
        String input = "\n";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        customerMenuState = new CustomerMenuState(null);
        //when
        String result = customerMenuState.defineAttribute(BLANK_INPUT_ALLOWED, input);
        //then
        Assertions.assertThat(result).isBlank();
    }

    @Test
    void shouldReturnNullForZeroInput() {
        //given
        String input = "0";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        customerMenuState = new CustomerMenuState(null);
        //when
        String result = customerMenuState.defineAttribute(BLANK_INPUT_ALLOWED, input);
        //then
        Assertions.assertThat(result).isNull();
    }
}
