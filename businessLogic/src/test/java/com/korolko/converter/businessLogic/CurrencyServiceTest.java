package com.korolko.converter.businessLogic;

import com.korolko.converter.entity.Currency;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CurrencyServiceTest {

    @Test
    public void convertTest() {
        final Currency current = new Currency(1L, "Российский рубль", "RUB", 1d, 1);
        final Currency target = new Currency(1L, "Доллар США", "USD", 2d, 1);

        System.out.println(current.convertTo(target, 1));
    }
}
