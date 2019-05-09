package com.korolko.converter.service;

import com.korolko.converter.domain.Currency;
import static org.junit.Assert.*;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class CurrencyConverterTest {

    private Currency current;
    private Currency target;

    @Before
    public void setUp() {
        current = new Currency("Current currency", "CUR", 1, 1);
        target = new Currency("Target currency", "TAR", 1.9901d, 1);
    }

    @Test
    public void convertTest() {
        BigDecimal result = CurrencyConverter.convert(current, target, 4);
        assertThat(BigDecimal.valueOf(2.01d), Matchers.comparesEqualTo(result));
    }
}
