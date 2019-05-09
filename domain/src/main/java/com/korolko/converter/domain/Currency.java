package com.korolko.converter.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @JsonProperty(value = "Cur_Name", access = JsonProperty.Access.READ_WRITE)
    private String name;

    @JsonProperty(value = "Cur_Abbreviation", access = JsonProperty.Access.READ_WRITE)
    private String abbreviation;

    @JsonProperty(value = "Cur_OfficialRate", access = JsonProperty.Access.WRITE_ONLY)
    private double rate;

    @JsonProperty(value = "Cur_Scale", access = JsonProperty.Access.WRITE_ONLY)
    private int scale;
}
