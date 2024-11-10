package store.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.util.Parser;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    @Test
    void parse_메서드_테스트_정상() {
        //given
        String input = "[콜라-10],[사이다-3],[아샷추-5]";
        //when
        Map<String, Integer> result = parser.parse(input);
        //then
        assertEquals(3, result.size());
        assertEquals(10, result.get("콜라"));
        assertEquals(3, result.get("사이다"));
        assertEquals(5, result.get("아샷추"));

        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}