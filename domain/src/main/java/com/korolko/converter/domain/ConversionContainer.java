package com.korolko.converter.domain;

import lombok.*;

import java.math.BigDecimal;

/**
 * Date: 04.18.2020
 *
 * @author Aleh Karalko
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ConversionContainer {

    String sourceAbbreviation;
    String targetAbbreviation;
    BigDecimal amount;
}
