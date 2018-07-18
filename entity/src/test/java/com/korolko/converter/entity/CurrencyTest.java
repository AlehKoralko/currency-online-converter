package com.korolko.converter.entity;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CurrencyTest {

    @Test
    public void convertTo() {
        final Currency current = new Currency("Беллоруский рубль", "BYN", 1d, 1);
        final Currency target = new Currency("Доллар США", "USD", 2d, 1);

        Assert.assertEquals(5, current.convertTo(target, 10), 0);
    }
}
