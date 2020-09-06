package view;

import model.Product;
import model.ProductType;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductMenuStateTest {
    private static ByteArrayInputStream mockInput;
    private static ProductMenuState productMenuState;

    private static final boolean BLANK_INPUT_ALLOWED = true;
    private static final boolean BLANK_INPUT_NOT_ALLOWED = false;
    private static final boolean ZERO_ALLOWED = true;
    private static final boolean ZERO_NOT_ALLOWED = false;
    private static final int BLANK_INPUT_MARKER = -1;

    @Test
    void shouldReturnProperDoubleValue() {
        //given
        String input = "5\n";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        productMenuState = new ProductMenuState(null);
        //when & then
        assertEquals(5.0, productMenuState.requestNumberInput(BLANK_INPUT_ALLOWED));
    }

    @Test
    void shouldReturnBlankInputMarkerValue() {
        //given
        String input = "\n";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        productMenuState = new ProductMenuState(null);
        //when & then
        assertEquals(BLANK_INPUT_MARKER,
                     productMenuState.requestNumberInput(BLANK_INPUT_ALLOWED));
    }

    @Test
    void shouldReturnProperLocalDate() {
        //given
        String input = "2020-09-15";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        productMenuState = new ProductMenuState(null);
        //when & then
        assertEquals(LocalDate.of(2020, 9, 15),
                     productMenuState.requestDateInput(BLANK_INPUT_NOT_ALLOWED));
    }

    @Test
    void shouldReturnLocalDateObjectWithCurrentDate() {
        //given
        String input = "\n";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        productMenuState = new ProductMenuState(null);
        //when & then
        assertEquals(LocalDate.now(),
                     productMenuState.requestDateInput(BLANK_INPUT_ALLOWED));
    }

    @Test
    void shouldReturnTrueForProperNumberInput() {
        //given
        int input = ProductType.DISC_DRIVE.ordinal();
        productMenuState = new ProductMenuState(null);
        //when & then
        assertTrue(productMenuState.verifyInputInRangeOfEnumTypes(
                ProductType.class, ZERO_NOT_ALLOWED, input));
    }

    @Test
    void shouldReturnFalseForWrongNumberInput() {
        //given
        int input = ProductType.values().length + 1;
        productMenuState = new ProductMenuState(null);
        //when & then
        assertFalse(productMenuState.verifyInputInRangeOfEnumTypes(
                ProductType.class, ZERO_NOT_ALLOWED, input));
    }

    @Test
    void shouldReturnTrueForZeroInput() {
        //given
        int input = 0;
        productMenuState = new ProductMenuState(null);
        //when & then
        assertTrue(productMenuState.verifyInputInRangeOfEnumTypes(
                ProductType.class, ZERO_ALLOWED, input));
    }

    @Test
    void shouldReturnFalseForZeroInput() {
        //given
        int input = 0;
        productMenuState = new ProductMenuState(null);
        //when & then
        assertFalse(productMenuState.verifyInputInRangeOfEnumTypes(
                ProductType.class, ZERO_NOT_ALLOWED, input));
    }

    @Test
    void shouldReturnFalseForProperObject() {
        //given
        Product product = new Product("sampleName", ProductType.CPU, 155.50, 5);
        int nullsThresholdForProperProduct = 1;
        productMenuState = new ProductMenuState(null);
        //when & then
        assertFalse(productMenuState.validateObjectFieldsNonNull(
                product, nullsThresholdForProperProduct));
    }

    @Test
    void shouldReturnTrueForObjectWithoutName() {
        //given
        Product product = new Product(null, ProductType.CPU, 155.50, 5);
        int nullsThresholdForProperProduct = 1;
        productMenuState = new ProductMenuState(null);
        //when & then
        assertTrue(productMenuState.validateObjectFieldsNonNull(
                product, nullsThresholdForProperProduct));
    }

    @Test
    void shouldReturnTrueForObjectWithoutType() {
        //given
        Product product = new Product("sampleName", null, 155.50, 5);
        int nullsThresholdForProperProduct = 1;
        productMenuState = new ProductMenuState(null);
        //when & then
        assertTrue(productMenuState.validateObjectFieldsNonNull(
                product, nullsThresholdForProperProduct));
    }

    @Test
    void shouldAssignValuesToProductFields() throws IOException {
        //given
        System.setIn(Files.newInputStream(
                Path.of("src/main/resources/setProductFields.txt")));
        productMenuState = new ProductMenuState(null);
        Product product = new Product();
        //when
        productMenuState.setProductFields(
                product, BLANK_INPUT_NOT_ALLOWED, ZERO_NOT_ALLOWED);
        //then
        assertEquals("GTX1080", product.getName());
        assertEquals(ProductType.GPU, product.getType());
        assertEquals(1390, product.getPrice());
        assertEquals(5, product.getAmount());
    }

    @Test
    void shouldReturnProperProductName() {
        //given
        String input = "Core i7-9700k";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        productMenuState = new ProductMenuState(null);
        //when
        String result = productMenuState.defineProductName(BLANK_INPUT_NOT_ALLOWED);
        //then
        assertEquals("Core i7-9700k", result);
    }

    @Test
    void shouldReturnBlankProductName() {
        //given
        String input = "\n";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        productMenuState = new ProductMenuState(null);
        //when
        String result = productMenuState.defineProductName(BLANK_INPUT_ALLOWED);
        //then
        assertEquals("", result);
    }

    @Test
    void shouldReturnProperProductPrice() {
        //given
        String input = "1275.33";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        productMenuState = new ProductMenuState(null);
        //when
        double result = productMenuState.defineProductPrice(BLANK_INPUT_NOT_ALLOWED);
        //then
        assertEquals(1275.33, result);
    }

    @Test
    void shouldReturnBlankInputMarkerInsteadOfPrice() {
        //given
        String input = "\n";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        productMenuState = new ProductMenuState(null);
        //when
        double result = productMenuState.defineProductPrice(BLANK_INPUT_ALLOWED);
        //then
        assertEquals(BLANK_INPUT_MARKER, result);
    }

    @Test
    void shouldReturnProperProductAmount() {
        //given
        String input = "17";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        productMenuState = new ProductMenuState(null);
        //when
        int result = productMenuState.defineAmountInStock(BLANK_INPUT_NOT_ALLOWED);
        //then
        assertEquals(17, result);
    }

    @Test
    void shouldReturnBlankInputMarkerInsteadOfAmount() {
        //given
        String input = "\n";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        productMenuState = new ProductMenuState(null);
        //when
        int result = productMenuState.defineAmountInStock(BLANK_INPUT_ALLOWED);
        //then
        assertEquals(BLANK_INPUT_MARKER, result);
    }
}
