package com.korolko.converter.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency implements Comparable<Currency> {

    @JsonProperty(value = "Cur_Name", access = JsonProperty.Access.READ_WRITE)
    private String name;

    @JsonProperty(value = "Cur_Abbreviation", access = JsonProperty.Access.READ_WRITE)
    private String abbreviation;

    @JsonProperty(value = "Cur_OfficialRate", access = JsonProperty.Access.WRITE_ONLY)
    private BigDecimal rate;

    @JsonProperty(value = "Cur_Scale", access = JsonProperty.Access.WRITE_ONLY)
    private int scale;

    private BigDecimal rateOfOne;

    @Override
    public int compareTo(Currency other) {
        return new CompareToBuilder()
                .append(this.name, other.name)
                .toComparison();
    }
}
