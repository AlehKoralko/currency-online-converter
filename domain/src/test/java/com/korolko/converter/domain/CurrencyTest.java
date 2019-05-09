package com.korolko.converter.domain;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CurrencyTest {

    @Test
    public void convertTo() {
        final Currency current = new Currency("Беллоруский рубль", "BYN", 1d, 1);
        final Currency target = new Currency("Доллар США", "USD", 1.9901d, 1);

        Assert.assertEquals("2.01", current.convertTo(target, 4).toString());
    }
}
