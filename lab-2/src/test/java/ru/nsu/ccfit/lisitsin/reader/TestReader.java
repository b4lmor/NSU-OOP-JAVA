package ru.nsu.ccfit.lisitsin.reader;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.ccfit.lisitsin.utils.FileOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class TestReader {

    private static final String TEST_FILE_NAME = "src/test/resources/test.txt";

    private CommandReader reader;

    @BeforeAll
    static void init() {
        FileOperations.createFile(TEST_FILE_NAME);
    }

    @BeforeEach
    void initReader() {
        reader = new CommandReader(TEST_FILE_NAME);
    }

    @AfterEach
    void clear() {
        FileOperations.clearFile(TEST_FILE_NAME);
        reader.close();
    }

    @AfterAll
    static void delete() {
        FileOperations.deleteFile(TEST_FILE_NAME);
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                "DEFINE a 4",
                                "PUSH a",
                                "",
                                "# print a",
                                "PRINT"
                        ),
                        List.of(
                                new CommandReader.CommandData(
                                        "DEFINE",
                                        new String[] {"a", "4"}
                                ),
                                new CommandReader.CommandData(
                                        "PUSH",
                                        new String[] {"a"}
                                ),
                                new CommandReader.CommandData(
                                        "PRINT",
                                        new String[] {}
                                )
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void testCommandReader(List<String> lines, List<CommandReader.CommandData> expectedResult) {
        FileOperations.writeToFile(TEST_FILE_NAME, lines);
        List<CommandReader.CommandData> commands = new ArrayList<>();

        CommandReader.CommandData command;
        while ((command = reader.readNextCommand()) != null) {
            commands.add(command);

        }
        assertIterableEquals(
                expectedResult,
                commands
        );
    }

}
