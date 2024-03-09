package ru.nsu.ccfit.lisitsin.calculator.instance;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.ccfit.lisitsin.calculator.instance.impl.DefaultCalculator;
import ru.nsu.ccfit.lisitsin.utils.FileOperations;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestCalculator {

    private static final String TEST_INPUT_FILE_NAME = "src/test/resources/in-test.txt";
    private static final String TEST_OUTPUT_FILE_NAME = "src/test/resources/out-test.txt";
    private static final String COMMAND_PROPERTIES_PATH = "/commands.properties";

    private DefaultCalculator calculator;

    @BeforeAll
    static void init() {
        FileOperations.createFile(TEST_INPUT_FILE_NAME);
        FileOperations.createFile(TEST_OUTPUT_FILE_NAME);
    }

    @BeforeEach
    void initCalculator() {
        calculator = new DefaultCalculator(FileOperations.getOutputStream(TEST_OUTPUT_FILE_NAME));
    }

    @AfterEach
    void clear() {
        FileOperations.clearFile(TEST_INPUT_FILE_NAME);
        FileOperations.clearFile(TEST_OUTPUT_FILE_NAME);
        if (calculator != null) {
            calculator.close();
        }
    }

    @AfterAll
    static void delete() {
        FileOperations.deleteFile(TEST_INPUT_FILE_NAME);
        FileOperations.deleteFile(TEST_OUTPUT_FILE_NAME);
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                "DEFINE a 4",
                                "PUSH a",
                                "SQRT",
                                "",
                                "# print a",
                                "PRINT"
                        ),
                        2.0
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void testCalculator(List<String> lines, double expectedResult) {
        FileOperations.writeToFile(TEST_INPUT_FILE_NAME, lines);
        calculator.execAll(COMMAND_PROPERTIES_PATH, TEST_INPUT_FILE_NAME);

        String rawResult = FileOperations.readFirstLine(TEST_OUTPUT_FILE_NAME);
        if (rawResult == null) {
            fail();
        }
        double result = Double.parseDouble(rawResult);

        assertEquals(
                expectedResult,
                result
        );
    }

}
