package com.korolko.converter.service;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
