package store.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.stockProcessStrategy.PromotionStockStrategy;
import store.model.stockProcessStrategy.RegularStockStrategy;

class StockProcessorFactoryTest {
    private StockProcessorFactory factory;

    @BeforeEach
    void setUp() {
        factory = new StockProcessorFactory();
    }

    @Test
    @DisplayName("프로모션 상품이 아니면 일반 재고 처리기를 StockProcessorFactory로 부터 리턴받으면 성공한다")
    void 프로모션_상품이_아니면_일반_재고처리기를_리턴받으면_성공한다() {
        //given
        //when
        StockProcessor processor = factory.getStrategy(Boolean.FALSE);
        //then
        Assertions.assertThat(processor).isInstanceOf(RegularStockStrategy.class);
    }
    @Test
    @DisplayName("프로모션 상품이지만, 프로모션 기간에 해당하지 않으면 일반 재고 처리기를 StockProcessorFactory로 부터 리턴받으면 성공한다")
    void 프로모션_기간에_해당하지_않으면면_일반_재고처리기를_리턴받으면_성공한다() {
        //given
        //when
        StockProcessor processor = factory.getStrategy(Boolean.FALSE);
        //then
        Assertions.assertThat(processor).isInstanceOf(RegularStockStrategy.class);
    }
    @Test
    @DisplayName("프로모션 기간에 해당하여 프로모션 재고처리기를 StockProcessorFactory로 부터 리턴받으면 성공한다")
    void 프로모션_기간에_해당하면_프로모션_재고처리기를_리턴받으면_성공한다() {
        //given
        //when
        StockProcessor processor = factory.getStrategy(Boolean.TRUE);
        //then
        Assertions.assertThat(processor).isInstanceOf(PromotionStockStrategy.class);
    }

}