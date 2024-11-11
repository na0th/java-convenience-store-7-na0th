package store.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.util.Parser;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[콜라-10],[사이다-3],[아샷추-5]"
    })
    @ValueSource(strings = {
            "[콜라-10],[사이다-3],[아샷추-5]",    // 기본 테스트 케이스
            "[콜라-2]",                           // 단일 항목
            "[물-1],[주스-10]",                   // 두 개의 항목
            "[빵-15],[우유-20],[초코우유-5]",     // 세 개의 항목
            "[오렌지-7],[사과-3],[바나나-4],[키위-8]", // 네 개의 항목
            "[아이스크림-1],[케이크-3],[빵-2],[커피-10],[녹차-7]" // 다섯 개의 항목
    })
    void parse_메서드_테스트_정상(String input) {
        // when
        Map<String, Integer> result = Parser.parse(input);

        // then
        assertNotNull(result);
        result.forEach((product, quantity) -> System.out.println(product + ": " + quantity));

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[고구마-1]]",
            "[밤]",
            "[[밤고구마-5]",
            "고구마-1]",
            "[고구마-1",
            "[고구마--1]",
            "[고구마-abc]",
            "[고구마-]",
            "[]",
            "[고구마]",
            "고구마-1",
//            "[ 고구마 - 1 ]", 공백은 봐준다
            "[사과-2], [배-]",
            "[사과-2], 배-2]",
            "[사이다-1],,",
            ",[사이다-1]",
            ",,"
    })
    void parse_메서드_테스트_예외_발생(String input) {
        //given
        //when & then
        assertThatThrownBy(() -> Parser.parse(input))
                .isInstanceOf(IllegalArgumentException.class);

    }

}