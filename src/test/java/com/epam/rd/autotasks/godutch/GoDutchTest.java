package com.epam.rd.autotasks.godutch;

import org.junit.jupiter.params.ParameterizedTest;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import java.util.stream.Stream;


public class GoDutchTest {

    @ParameterizedTest
    @MethodSource("dataProvider")
    void correctInputTest(String totalBill, String numberOfFriends, String partToPay) {
        String input = totalBill + System.lineSeparator() + numberOfFriends;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        BufferedInputStream controlledIn = new BufferedInputStream(inputStream);
        InputStream defaultIn = System.in;
        System.setIn(controlledIn);

        final ByteArrayOutputStream sink = new ByteArrayOutputStream();
        PrintStream controlledOut = new PrintStream(sink);
        PrintStream defaultOut = System.out;
        System.setOut(controlledOut);

        try {
            GoDutch.main(new String[]{});
            controlledOut.flush();
            final String actual = sink.toString().trim();
            assertEquals(partToPay, actual);
        } finally {
            System.setIn(defaultIn);
            System.setOut(defaultOut);
        }

    }

    static Stream<Arguments> dataProvider() {
        return Stream.of(
                arguments("10000", "5", "2200"),
                arguments("5000", "1", "5500"),
                arguments("200", "220", "1")
        );
    }

}
