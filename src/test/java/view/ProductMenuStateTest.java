package view;

import controller.ProductController;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMenuStateTest {
    private static ByteArrayInputStream mockInput;
    private static ProductController productController =
            new ProductController(new ArrayDeque<>());
    private static ProductMenuState productMenuState;

    private static final boolean BLANK_INPUT_ALLOWED = true;
    private static final int BLANK_INPUT_MARKER = -1;

    @Test
    void shouldReturnProperDoubleValue() {
        String input = "5\n";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        productMenuState = new ProductMenuState(productController);
        assertEquals(5.0, productMenuState.requestNumberInput(BLANK_INPUT_ALLOWED));
    }

    @Test
    void shouldReturnBlankInputMarkerValue() {
        String input = "\n";
        mockInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockInput);
        productMenuState = new ProductMenuState(productController);
        assertEquals(BLANK_INPUT_MARKER,
                     productMenuState.requestNumberInput(BLANK_INPUT_ALLOWED));
    }
}
